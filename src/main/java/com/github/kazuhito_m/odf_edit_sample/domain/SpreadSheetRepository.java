package com.github.kazuhito_m.odf_edit_sample.domain;

import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;

public interface SpreadSheetRepository {

    ResponseEntity<byte[]> makeDownloadEntryForOds(SpreadSheetReportMaker reportMaker) throws IOException;

    File makeReport(SpreadSheetReportMaker reportMaker) throws IOException;
}
