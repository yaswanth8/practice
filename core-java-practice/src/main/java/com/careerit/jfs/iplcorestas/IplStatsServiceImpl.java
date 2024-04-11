package com.careerit.jfs.iplcorestas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IplStatsServiceImpl implements IplsStatsService{

    private List<Player> players;

    public IplStatsServiceImpl() {
        players= PlayerDataReaderUtil.loadPlayersData();
    }

    @Override
    public List<String> teamNames() {
         /*List<String> teamNames=new ArrayList<>();
         for(Player player:players) {
             if (!teamNames.contains(player.getTeam())){
                 teamNames.add(player.getTeam());
             }
         }
        return teamNames;*/
    return players.stream()
            .map(Player::getTeam)
            .distinct()
            .toList();

    }

    @Override
    public List<Player> playersByTeamName(String teamName) {

        /*List<Player> teamPlayers=new ArrayList<>();
        for(Player player:players){
            if(player.getTeam().equalsIgnoreCase(teamName)){
                teamPlayers.add(player);
            }
        }

        return teamPlayers;*/

        return players.stream()
                .filter(player-> player.getTeam().equalsIgnoreCase(teamName))
                .toList();
    }

    @Override
    public List<TeamRoleCountDto> roleCountByTeam(String teamName) {
        List<Player> teamPlayers=playersByTeamName(teamName);
        Map<String,Integer> roleCountMap=new HashMap<>();
        for(Player player:teamPlayers){
            String role= player.getRole();
            roleCountMap.put(role,roleCountMap.getOrDefault(role,0)+1);
        }



        return getteamRoleCountDtos(teamName,roleCountMap);
    }

    @Override
    public List<TeamRoleAmountRecord> roleAmountByTeam(String teamName) {
        return null;
    }

    @Override
    public List<TeamAmountRecord> amountByTeam() {
        return null;
    }

    @Override
    public List<TeamCountRecord> playerCountOFEachTeam() {
        return null;
    }

    @Override
    public List<Player> getTopPaidPlayers() {
        return null;
    }

    @Override
    public List<Player> getTopPaidPlayers(int n) {
        return null;
    }

    public static List<TeamRoleCountDto> getteamRoleCountDtos(String teamName,Map<String,Integer> roleCount){
        List<TeamRoleCountDto> teamRoleCountDtos=new ArrayList<>();

        for(Map.Entry<String,Integer> entry: roleCount.entrySet()){
            String roleName= entry.getKey();
            int count = entry.getValue();
            TeamRoleCountDto teamRoleCountDto=new TeamRoleCountDto(teamName,roleName,count);
            teamRoleCountDtos.add(teamRoleCountDto);

        }
        return teamRoleCountDtos;
    }
}
