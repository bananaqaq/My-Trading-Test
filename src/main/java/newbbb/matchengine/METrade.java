package newbbb.matchengine;

import newbbb.model.me.Market;
import java.util.HashMap;

public class METrade {

    private static HashMap<String, Market> markets;

    public int InitTrade(){
        markets = new HashMap<>();
        return 0;
    }

    public Market getMarket(String name){
        return markets.get(name);
    }

}
