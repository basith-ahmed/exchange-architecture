public class SimpleMovingAverageStrategy implements TradingStrategy {
    @Override
    public void execute(MarketData data) {
        // Simple strategy example
        if (data.getPrice() > 150) {
            System.out.println("Buying " + data.getSymbol());
        } else {
            System.out.println("Selling " + data.getSymbol());
        }
    }
}
