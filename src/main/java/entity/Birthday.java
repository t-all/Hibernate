package entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public record Birthday(LocalDate birthDay) {
    public  long getAge() {
        return ChronoUnit.YEARS.between(birthDay, LocalDate.now());
    }
}
