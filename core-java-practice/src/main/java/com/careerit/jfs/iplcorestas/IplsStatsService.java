package com.careerit.jfs.iplcorestas;
import java.util.List;
public interface IplsStatsService {

    List<String> teamNames();

    List<Player> playersByTeamName(String teamName);

    List<TeamRoleCountDto > roleCountByTeam(String teamName);

    List<TeamRoleAmountRecord> roleAmountByTeam(String teamName);

    List<TeamAmountRecord> amountByTeam();

    List<TeamCountRecord> playerCountOFEachTeam();

    List<Player> getTopPaidPlayers();

    List<Player> getTopPaidPlayers(int n);

}
