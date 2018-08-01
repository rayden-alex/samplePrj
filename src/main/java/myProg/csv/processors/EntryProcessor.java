package myProg.csv.processors;

public interface EntryProcessor<T> {
    void process(T entry);

    void saveToDB();
}
