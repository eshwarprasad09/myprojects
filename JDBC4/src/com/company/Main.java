package com.company;

import java.sql.*;
import java.util.*;

public class Main {

    public static final int MATCH_ID = 1;
    public static final int SEASON = 2;
    public static final int WINNER = 11;
    public static final int BATSMAN = 7;
    public static final int BATSMEN_RUNS = 16;
    public static final int TOTAL_RUNS = 18;
    public static final int EXTRA_RUNS = 17;
    public static final int BATTING_TEAM = 3;
    public static final int BOWLER_NAMES = 9;

    public static void main(String[] args) {
        List<Match> matches = getMatchesData();
        List<Delivery> deliveries = getDeliveriesData();
        List<Integer> allMatchesId2015 = getEveryMatchId(matches,2015);
        List<Integer> allMatchesId2016 = getEveryMatchId(matches,2016);
        Main.findMatchesInEveryYear(matches);
        Main.findTeamWonMatchesInSeason(matches);
        Main.findExtraRunsConcededByTeam(deliveries,allMatchesId2016);
        Main.findTopEconomyBowlers(deliveries,allMatchesId2015);
        Main.findEveryBatsmenRunsInYear(deliveries,allMatchesId2015);
    }

    public static Connection get_Connection(){
        Connection connection = null;
        String jdbcURL = "jdbc:postgresql://localhost:5432/iplproject";
        String username = "postgres";
        String password = "root";
        try {
            connection = DriverManager.getConnection(jdbcURL,username,password);
            System.out.println("connected to postgresql server \n");
        } catch (SQLException e) {
            System.out.println("Error in connecting to postgreSQL server");
            e.printStackTrace();
        }
        return connection;
    }

    private static List<Delivery> getDeliveriesData() {
        List<Delivery> deliveries = new ArrayList<>();
        Connection connection = get_Connection();
        String query = "SELECT * FROM deliveries";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Delivery delivery = new Delivery();
                delivery.setBatsmenName(resultSet.getString(BATSMAN));
                delivery.setBatsmenRuns(resultSet.getInt(BATSMEN_RUNS));
                delivery.setBowlerTotalRuns(resultSet.getInt(TOTAL_RUNS));
                delivery.setExtraRuns(resultSet.getInt(EXTRA_RUNS));
                delivery.setMatchId(resultSet.getInt(MATCH_ID));
                delivery.setBattingTeam(resultSet.getString(BATTING_TEAM));
                delivery.setBowlerName(resultSet.getString(BOWLER_NAMES));

                deliveries.add(delivery);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deliveries;
    }

    private static List<Match> getMatchesData() {
        List<Match> matches = new ArrayList<>();
        Connection connection = get_Connection();
        String query = "SELECT * FROM matches";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Match match = new Match();

                match.setMatchId(resultSet.getInt(MATCH_ID));
                match.setSeason(resultSet.getInt(SEASON));
                match.setWinner(resultSet.getString(WINNER));

                matches.add(match);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return matches;
    }
    public static List<Integer> getEveryMatchId(List<Match> matches, int year) {
        List<Integer> everyMatchId = new ArrayList<>();
        int everyMatchYear = year;
        for (Match match : matches) {
            int everyMatchSeason = match.getSeason();
            if (everyMatchSeason == everyMatchYear) {
                int everyMatchYearId = match.getMatchId();
                everyMatchId.add(everyMatchYearId);
            }
        }
        return everyMatchId;
    }
    public static void findMatchesInEveryYear(List<Match> matches) {
        HashMap<Integer, Integer> matchesPerYear = new HashMap<>();
        for (Match match : matches) {
            int year = match.getSeason();
            if (matchesPerYear.containsKey(year)) {
                matchesPerYear.put(year, (matchesPerYear.get(year)) + 1);
            } else {
                matchesPerYear.put(year, 1);
            }
        }
        System.out.println("Matches played in every season " + matchesPerYear);
    }

    public static void findTeamWonMatchesInSeason(List<Match> matches) {
        HashMap<String, Integer> teamWon = new HashMap<>();
        for (Match match : matches) {
            String teamName = match.getWinner();
            if (teamWon.containsKey(teamName)) {
                teamWon.put(teamName, (teamWon.get(teamName)) + 1);
            } else {
                if (!Objects.equals(teamName, "")) {
                    teamWon.put(teamName, 1);
                }
            }
        }
        System.out.println("Team won matches in every season " + teamWon);
    }
    public static void findExtraRunsConcededByTeam(List<Delivery> deliveries, List<Integer> allMatchesId) {
        HashMap<String, Integer> extraRunsConcededByTeam = new HashMap<>();
        for (Delivery delivery : deliveries) {
            int matchId = delivery.getMatchId();
            int extraRuns = delivery.getExtraRuns();
            String teamName = delivery.getBattingTeam();
            if (allMatchesId.contains(matchId)) {
                if (extraRunsConcededByTeam.containsKey(teamName)) {
                    extraRunsConcededByTeam.put(teamName, (extraRunsConcededByTeam.get(teamName)) + extraRuns);
                } else {
                    extraRunsConcededByTeam.put(teamName, extraRuns);
                }
            }
        }
        System.out.println("Extra runs conceded by team in 2016 " + extraRunsConcededByTeam);
    }

    public static void findTopEconomyBowlers(List<Delivery> deliveries, List<Integer> allMatchesId){
        HashMap<String,Integer>totalBalls = new HashMap<>();
        HashMap<String,Integer>runsByBowler = new HashMap<>();
        HashMap<String,Float>economyAll = new HashMap<>();
        List<String>bowlerNames = new ArrayList<>();
        for(Delivery delivery : deliveries){
            int matchId = delivery.getMatchId();
            String bowler = delivery.getBowlerName();
            int totalRuns = delivery.getBowlerTotalRuns();
            if (allMatchesId.contains(matchId)) {
                if (totalBalls.containsKey(bowler)) {
                    totalBalls.put(bowler, (totalBalls.get(bowler)) + 1);
                    runsByBowler.put(bowler, (runsByBowler.get(bowler)) + totalRuns);
                } else {
                    totalBalls.put(bowler, 1);
                    runsByBowler.put( bowler, totalRuns);
                    bowlerNames.add(bowler);
                }
            }
        }
        for(String names : bowlerNames){
            float runs = runsByBowler.get(names);
            float balls = totalBalls.get(names);
            float economy = (runs/balls) * 6;
            economyAll.put(names,economy);
        }
        List<Map.Entry<String, Float>> topEconomyBowlers = new LinkedList<Map.Entry<String, Float>>(economyAll.entrySet());
        Collections.sort(topEconomyBowlers, new Comparator<Map.Entry<String, Float>>() {
            public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        System.out.println("Top economy bowlers of 2015 " + topEconomyBowlers);
    }
    public static void findEveryBatsmenRunsInYear(List<Delivery> deliveries, List<Integer> allMatchesId){
        HashMap<String,Integer>batsmenRuns = new HashMap<>();
        for (Delivery delivery : deliveries){
            int everyMatchId = delivery.getMatchId();
            String key = delivery.getBatsmenName();
            int value = delivery.getBatsmenRuns();
            if(allMatchesId.contains(everyMatchId)){
                if(batsmenRuns.containsKey(key)){
                    batsmenRuns.put(key,(batsmenRuns.get(key) + value));
                }
                else {
                    batsmenRuns.put(key,value);
                }
            }
        }
        LinkedHashMap<String, Integer> everyBatsmenRuns = new LinkedHashMap<>();
        batsmenRuns.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> everyBatsmenRuns.put(x.getKey(), x.getValue()));
        System.out.println("Every batsmen total runs in 2015 " + everyBatsmenRuns);
    }
}

