import java.util.ArrayList;
import java.util.List;

public class TradingEngine {
    private List<TradingStrategy> strategies = new ArrayList<>();

    public void addStrategy(TradingStrategy strategy) {
        strategies.add(strategy);
    }

    public void onMarketData(MarketData data) {
        for (TradingStrategy strategy : strategies) {
            strategy.execute(data);
        }
    }

    public static void main(String[] args) {
        TradingEngine engine = new TradingEngine();
        engine.addStrategy(new SimpleMovingAverageStrategy());

        MarketData data = new MarketData("AAPL", 150.25);
        engine.onMarketData(data);
    }
}
