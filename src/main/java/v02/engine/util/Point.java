package v02.engine.util;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


@Getter
@EqualsAndHashCode(exclude = "parent")
@ToString(exclude = "parent")
@AllArgsConstructor
public class Point {
    private int x;
    private int y;
    private Point parent;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
