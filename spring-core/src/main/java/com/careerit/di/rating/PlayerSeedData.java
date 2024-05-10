package com.careerit.di.rating;

import org.springframework.stereotype.Component;

import java.util.List;



@Component
public class PlayerSeedData {



    public List<Player> getPlayers(){
        List<Player> players=List.of(new Player("VK","Batsman","India"),
                new Player("YK","Bowler","India")
        );

        return players;
    }


}
