package jp.issei.omizu.weghtcalendar.data.realm;

import java.util.Date;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by BestRun on 2017/06/07.
 */

@Getter
@Setter
public class PhysicalMeasurement extends RealmObject {
    @PrimaryKey
    private String id;
    private Date date;
    private Float weight;
    private Float bodyFatPercentage;
    private Float bodyTemperature;
}
