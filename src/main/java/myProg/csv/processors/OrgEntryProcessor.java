package myProg.csv.processors;

import myProg.csv.entries.OrgEntry;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class OrgEntryProcessor implements EntryProcessor<OrgEntry> {
    @Override
    public void process(OrgEntry entry) {
       //
    }
}
