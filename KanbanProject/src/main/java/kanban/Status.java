package kanban;

public enum Status {
    Backlog,
    InProgress,
    InReview,
    Test,
    Finished;

    public Status increment(Status s){
        if (s == Backlog)
            return InProgress;
        else if (s == InProgress)
            return InReview;
        else if (s == InReview)
            return Test;
        else if (s == Test)
            return Finished;
        return s;
    }
    public Status decrement(Status s){
        if (s == Finished)
            return Test;
        else if (s == Test)
            return InReview;
        else if (s == InReview)
            return InProgress;
        else if (s == InProgress)
            return Backlog;
        return s;
    }
}
