package myProg.csv.processors;

import myProg.csv.entries.OrgEntry;
import org.springframework.stereotype.Component;

@Component
public class OrgEntryProcessor implements EntryProcessor<OrgEntry> {
    @Override
    public void process(OrgEntry entry) {
       //
    }
}
