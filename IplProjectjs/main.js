const MATCH_ID = 0;
const SEASON = 1;
const WINNER = 10;
const BATTING_TEAM = 2;
const EXTRA_RUNS = 16;
const BOWLER_NAME = 8;
const TOTAL_RUNS = 17;
const BATSMEN_NAME = 6;
const BATSMEN_RUNS = 15;

function getMatchesData(){
   var fs = require("fs");
   var data = fs.readFileSync("matches.csv",{encoding:'utf-8'});
   var matchData = data.split('\r\n');
   var matchesData = [];
   for(var row = 1; row<matchData.length-1; row++){
       var rowData = matchData[row].split(",");
       matchesData.push(rowData);
   };
   return matchesData;
}

function getDeliveriesData(){
   var fs = require("fs");
   var data = fs.readFileSync("deliveries.csv",{encoding:'utf-8'});
   var deliveryData = data.split('\r\n');
   var deliveriesData = [];
   for(var row = 1; row<deliveryData.length; row++){
       var rowData = deliveryData[row].split(",");
       deliveriesData.push(rowData);
   };
   return deliveriesData;
}

function getMatchId(matchesData,id){
   var matchId = []
   var id = id.toString()
   for(let row = 0; row < matchesData.length; row++){
      if(matchesData[row][SEASON] == id){
         matchId.push(matchesData[row][MATCH_ID])
      }
   }
   return matchId;
}

let matchesData = getMatchesData();
findMatchesInEverySeason(matchesData)
findMatchesWonPerTeam(matchesData)
let deliveryData = getDeliveriesData()
let matchesId2016 = getMatchId(matchesData,2016)
findExtraRunsConcededByTeam(deliveryData,matchesId2016)
let matchesId2015 = getMatchId(matchesData,2015)
findTopEconomyBowlers(deliveryData,matchesId2015)
findBatsmenTotalRuns(deliveryData,matchesId2016)


function findMatchesInEverySeason(matchesData){
   let year = {};
   for(var row = 0;row<matchesData.length; row++){
       if(year.hasOwnProperty(matchesData[row][SEASON])){
           year[matchesData[row][SEASON]] +=1;
       }
       else{
           year[matchesData[row][SEASON]] =1;
       }
   }
   console.log(year)
}

function findMatchesWonPerTeam(matchesData){
   let matchesWonPerTeam = {}; 
   for(var row = 0;row<matchesData.length; row++){
       if(matchesWonPerTeam.hasOwnProperty(matchesData[row][WINNER])){
         matchesWonPerTeam[matchesData[row][WINNER]] +=1;
       }
       else{
         if(matchesData[row][WINNER] != "")
         matchesWonPerTeam[matchesData[row][WINNER]] =1;
       }
   }
   console.log(matchesWonPerTeam)
}

function findExtraRunsConcededByTeam(deliveryData,matchesId){
   let extraRunsConcededByTeam = {}
   for(let row = 0; row < deliveryData.length; row++){
      let id = (deliveryData[row][MATCH_ID])
      let battingTeam = deliveryData[row][BATTING_TEAM]
      let extraRuns = parseInt(deliveryData[row][EXTRA_RUNS])
      if(matchesId.includes(id)){
         if(extraRunsConcededByTeam.hasOwnProperty(battingTeam)){
            extraRunsConcededByTeam[battingTeam] += extraRuns
         }
         else{
            extraRunsConcededByTeam[battingTeam] = extraRuns
         }
      }
   }
   console.log(extraRunsConcededByTeam)
}

function findTopEconomyBowlers(deliveryData,matchesId){
   let topEconomyBowlers = {}
   let bowlerBalls = {}
   let bowlerRuns = {}
   let bowlers = []
   for(let row = 0; row < deliveryData.length; row++){
      let bowlerName = deliveryData[row][BOWLER_NAME]
      let bowlerTotalRuns = parseInt(deliveryData[row][TOTAL_RUNS])
      let id = (deliveryData[row][MATCH_ID])
      if(matchesId.includes(id)){
         if(bowlerRuns.hasOwnProperty(bowlerName)){
            bowlerRuns[bowlerName] += bowlerTotalRuns
            bowlerBalls[bowlerName] += 1
         }
         else{
            bowlerRuns[bowlerName] = bowlerTotalRuns
            bowlerBalls[bowlerName] = 1
         }
      }
   }
   bowlers = Object.keys(bowlerRuns)
   bowlers.forEach((names) => {
      let runs = parseFloat(bowlerRuns[names])
      let balls = parseFloat(bowlerBalls[names])
      let economy = (runs / balls) * 6
      topEconomyBowlers[names] = economy
   })
   const topEconomyBowlersSorted = Object.fromEntries(
      Object.entries(topEconomyBowlers).sort(([,a],[,b]) => a-b)
  );
  console.log(topEconomyBowlersSorted);
}

function findBatsmenTotalRuns(deliveryData,matchesId){
   let batsmenTotalRuns = {}
   for(let row = 0; row < deliveryData.length - 1; row++){
      let batsmenName = deliveryData[row][BATSMEN_NAME]
      let batsmenRuns = parseInt(deliveryData[row][BATSMEN_RUNS])
      let id = (deliveryData[row][MATCH_ID])
      if(matchesId.includes(id)){
         if(batsmenTotalRuns.hasOwnProperty(batsmenName)){
            batsmenTotalRuns[batsmenName] += batsmenRuns
         }
         else{
            batsmenTotalRuns[batsmenName] = batsmenRuns
         }
      }
      }
   console.log(batsmenTotalRuns)
}