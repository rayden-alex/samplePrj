package myProg.csv.processors;

import myProg.csv.entries.DeptEntry;
import org.springframework.stereotype.Component;

@Component
public class DeptEntryProcessor implements EntryProcessor<DeptEntry> {
    @Override
    public void process(DeptEntry entry) {
        //
    }
}
