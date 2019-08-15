package com.github.kazuhito_m.odf_edit_sample.domain.report;

import org.jopendocument.dom.spreadsheet.SpreadSheet;

public interface SpreadSheetReportMaker {
    void writeContent(SpreadSheet ods);

    String getReportDlName();

    String getTemplateFileName();
}
