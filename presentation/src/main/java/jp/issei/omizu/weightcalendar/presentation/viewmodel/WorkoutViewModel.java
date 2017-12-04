package jp.issei.omizu.weightcalendar.presentation.viewmodel;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import jp.issei.omizu.weightcalendar.presentation.frameworks.messengers.Messenger;
import jp.issei.omizu.weightcalendar.presentation.model.StopWatchModel;
import jp.keita.kagurazaka.rxproperty.Nothing;
import jp.keita.kagurazaka.rxproperty.ReadOnlyRxProperty;
import jp.keita.kagurazaka.rxproperty.RxCommand;
import jp.keita.kagurazaka.rxproperty.RxProperty;

public class WorkoutViewModel implements Disposable {

    // 簡易Messenger(EventBus のようなもの)
    public final Messenger messenger = new Messenger();

    private final CompositeDisposable _subscriptions = new CompositeDisposable();

    // ■ViewModel として公開するプロパティ

    /** タイマー時間 */
    public final ReadOnlyRxProperty<String> formattedTime;
    public final ReadOnlyRxProperty<String> trainingTime;
    public final ReadOnlyRxProperty<String> restTime;
    /** 実行中かどうか？ */
    public final ReadOnlyRxProperty<Boolean> isRunning;
    /** 経過時間群 */
    public final ReadOnlyRxProperty<List<String>> formattedLaps;
    /** ミリ秒を表示するか？ */
    public final RxProperty<Boolean> isVisibleMillis;

    public final RxCommand<Nothing> startOrStopCommand;
    public final RxCommand<Nothing> lapCommand;
    public final RxCommand<Nothing> toggleVisibleMillisCommand;

    StopWatchModel mStopWatch = null;

    // コンストラクタ
    @Inject
    public WorkoutViewModel(StopWatchModel stopWatch) {
        mStopWatch = stopWatch;

        // StopWatchModel のプロパティをそのまま公開してるだけ
        isRunning = new ReadOnlyRxProperty<>(stopWatch.isRunning);
        formattedLaps = new ReadOnlyRxProperty<>(stopWatch.formattedLaps);

        isVisibleMillis = new RxProperty<>(stopWatch.isVisibleMillis);

        // フォーマットされた時間を表す Observable（time と timeFormat のどちらかが変更されたら更新）
        // 表示用にthrottleで10ms毎に間引き。View側でやってもよいかも。
        formattedTime = new ReadOnlyRxProperty<>(stopWatch.formattedTime);
        trainingTime = new ReadOnlyRxProperty<>(stopWatch.trainingTime);
        restTime = new ReadOnlyRxProperty<>(stopWatch.restTime);

        // STOP されたら、最速／最遅ラップを表示して、LapActivity へ遷移
        _subscriptions.add(
                stopWatch.isRunning.filter(isRunning -> {
                    return !isRunning;
                })
                .subscribe(notUse -> {
//                    // Toast を表示させる
//                    messenger.send(new ShowToastMessages(
//                            "最速ラップ:" + stopWatch.getFormattedFastestLap() +
//                            ", 最遅ラップ:" + stopWatch.getFormattedWorstLap()));
//
//                    // LapActivity へ遷移させる
//                    messenger.send(new StartActivityMessage(LapActivity.class)); // ホントは LapViewModel を指定して画面遷移すべき
                }));

        /** 開始 or 終了 */
        startOrStopCommand = new RxCommand<>();
        startOrStopCommand.subscribe(n -> {
            stopWatch.startOrStop();
        });

        /** 経過時間の記録 */
        lapCommand = new RxCommand<>(isRunning);
        lapCommand.subscribe(it -> {
            stopWatch.lap();
        });

        /** ミリ秒以下表示の切り替え */
        toggleVisibleMillisCommand = new RxCommand<>();
        toggleVisibleMillisCommand.subscribe(it -> {
            stopWatch.toggleVisibleMillis();
        });
    }

    public void initialize(Context context)
    {
        mStopWatch.initialize(context);
    }

    @Override
    public void dispose() {
        _subscriptions.dispose();
        mStopWatch.unsubscribe();
    }

    @Override
    public boolean isDisposed() {
        return _subscriptions.isDisposed();
    }
}
