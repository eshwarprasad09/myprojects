public class Match {
    private int matchId;
    private int season;
    private String winner;
    private String everySeason;

    public String getEverySeason() {
        return everySeason;
    }
    public void setEverySeason(String everySeason) {
        this.everySeason = everySeason;
    }
    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

}