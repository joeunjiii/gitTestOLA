package com.smhrd.olaPJ.olasibal;

import com.smhrd.olaPJ.olasibal.Content;
import org.apache.commons.csv.*;
import org.springframework.stereotype.Component;
import java.io.*;
import java.nio.file.*;
import java.util.*;

@Component
public class CSVLoader {
    public List<Content> loadCSV(String path) throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get(path));
        CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());

        List<Content> list = new ArrayList<>();
        for (CSVRecord record : parser) {
            list.add(new Content(
                    record.get("제목"),
                    Arrays.asList(record.get("장르").split(",\\s*")),
                    Arrays.asList(record.get("OTT").split(",\\s*")),
                    Double.parseDouble(record.get("평점")),
                    0.0
            ));
        }
        return list;
    }
}

