package newbbb.matchengine;

import com.alibaba.fastjson.JSONArray;
import newbbb.model.me.Asset;
import newbbb.model.me.Market;
import newbbb.model.me.Settings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@Order(2)
public class MEConfig {

    private static Settings settings;

    @Value("${config.assets}")
    private String assetsJson;

    @Value("${config.markets}")
    private String marketsJson;

    @PostConstruct
    private void InitConfig(){

        settings = new Settings();
        List<Asset> assets = JSONArray.parseArray(assetsJson, Asset.class);
        settings.setAssetNum(assets.size());
        settings.setAssets(assets);

        List<Market> markets = JSONArray.parseArray(marketsJson, Market.class);
        settings.setMarketNum(markets.size());
        settings.setMarkets(markets);

    }

    public Settings settings(){
        return settings;
    }

}