package doc;

import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

/**
 * @Auther: xujunqian
 * @Date: 2019/9/9 0009 14:24
 * @Description:
 */
public class CellRange {

    public static final int COL_MERGE = 0;

    public static final int ROW_MERGE = 1;

    /**
     * 合并类型（0-col,1-row）
     **/
    private int mergeType;

    /**
     * 起始列
     **/
    private int startCol;

    /**
     * 结束列
     **/
    private int endCol;

    /**
     * 起始行
     **/
    private int startRow;

    /**
     * 结束行
     **/
    private int endRow;

    public CellRange(int startCol, int endCol, int startRow, int endRow, int mergeType) {
        this.startCol = startCol;
        this.endCol = endCol;
        this.startRow = startRow;
        this.endRow = endRow;
        this.mergeType = mergeType;
    }

    public CellRange(int startCol, int endCol, int startRow, int endRow) {
        this.startCol = startCol;
        this.endCol = endCol;
        this.startRow = startRow;
        this.endRow = endRow;
        this.mergeType = mergeType;
    }

    public void mergeCells(XWPFTable table) {
        switch (this.mergeType) {
            case CellRange.COL_MERGE:
                this.mergeCellsHorizontal(table);
                break;
            case CellRange.ROW_MERGE:
                this.mergeCellsVertically(table);
                break;
        }
    }

    /**
     * word跨行并单元格
     *
     * @param table
     */
    public void mergeCellsVertically(XWPFTable table) {
        for (int rowIndex = this.startRow; rowIndex <= this.endRow; rowIndex++) {
            XWPFTableCell cell = table.getRow(rowIndex).getCell(this.startCol);
            if (rowIndex == this.startRow) {
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
            } else {
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
            }
        }
    }

    /**
     * word跨列合并单元格
     *
     * @param table
     */
    public void mergeCellsHorizontal(XWPFTable table) {
        for (int cellIndex = this.startCol; cellIndex <= this.endCol; cellIndex++) {
            XWPFTableCell cell = table.getRow(this.startRow).getCell(cellIndex);
            if (cellIndex == this.startCol) {
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
            } else {
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
            }
        }
    }
}
