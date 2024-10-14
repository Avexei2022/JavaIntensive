package ru.kolodin.model.habitstatus;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.kolodin.model.habits.Habit;

import java.util.Date;

/**
 * Класс статуса выполнения привычки
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "habit_statuses")
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class HabitStatus {
    /**
     * Уникальный идентификатор статуса
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    /**
     * Привычка
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "habitId")
    private Habit habit;

    /**
     * Дата выполнения привычки
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;

    /**
     * Статус выполнения привычки
     */
    @Column(name = "status")
    private Status status;

    public HabitStatus(Habit habit, Date date, Status status) {
        this.habit = habit;
        this.date = date;
        this.status = status;
    }
}
