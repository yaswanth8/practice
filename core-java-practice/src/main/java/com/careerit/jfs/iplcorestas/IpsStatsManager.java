package com.careerit.jfs.iplcorestas;

import java.util.List;

public class IpsStatsManager {

    public static void main(String[] args) {
        List<Player> players=PlayerDataReaderUtil.loadPlayersData();
        System.out.println("Total players count "+players.size());
        Player player=players.get(0);
        System.out.println(player.getName());

        // 1. Get team names
        IplsStatsService obj=new IplStatsServiceImpl();
        List<String> teamNames=obj.teamNames();
        System.out.println("Team names : "+teamNames);
        // 2. Get players by team name
        String teamName ="RCB";
        System.out.println(obj.playersByTeamName(teamName));

        // 3. get role count of given team

        List<TeamRoleCountDto> teamRoleCountDtos=obj.roleCountByTeam(teamName);
                System.out.println(teamRoleCountDtos);
    }
}
