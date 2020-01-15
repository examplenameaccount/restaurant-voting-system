package com.javawebinar.restaurant.to;

import com.javawebinar.restaurant.model.Vote;

public class VoteTo {
    private Vote vote;
    private boolean created;

    public VoteTo() {
    }

    public VoteTo(Vote vote, boolean created) {
        this.vote = vote;
        this.created = created;
    }

    public Vote getVote() {
        return vote;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }

    public boolean isCreated() {
        return created;
    }

    public void setCreated(boolean created) {
        this.created = created;
    }
}
