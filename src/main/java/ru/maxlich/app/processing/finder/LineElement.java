package ru.maxlich.app.processing.finder;

/**
 * Created by mshulakov on 27.04.2018.
 */
//содержит информацию о элеенте строки и его позиции в строке
public class LineElement {
    private String lineElement;
    private int columnNum;

    public LineElement(String lineElement, int columnNum) {
        this.lineElement = lineElement;
        this.columnNum = columnNum;
    }

    public int getColumnNum() {
        return columnNum;
    }

    public void setColumnNum(int columnNum) {
        this.columnNum = columnNum;
    }

    public String getLineElement() {
        return lineElement;
    }

    public void setLineElement(String lineElement) {
        this.lineElement = lineElement;
    }
}
