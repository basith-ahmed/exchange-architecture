import java.util.List;

public class BacktestingEngine {
    private TradingStrategy strategy;

    public BacktestingEngine(TradingStrategy strategy) {
        this.strategy = strategy;
    }

    public void run(List<MarketData> historicalData) {
        for (MarketData data : historicalData) {
            strategy.execute(data);
        }
    }

    public static void main(String[] args) {
        TradingStrategy strategy = new SimpleMovingAverageStrategy();
        BacktestingEngine engine = new BacktestingEngine(strategy);

        List<MarketData> historicalData = List.of(
                new MarketData("AAPL", 145.0),
                new MarketData("AAPL", 150.0),
                new MarketData("AAPL", 155.0)
        );

        engine.run(historicalData);
    }
}
