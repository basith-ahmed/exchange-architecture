Building a high-performance trading platform involves many components. Below are simplified code samples to get you started on each major part of the system. These examples are basic and meant to demonstrate the core concepts. For a production system, you'll need to expand and refine these considerably.

### 1. Market Data Feeds

**MarketDataProducer.java**
```java
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class MarketDataProducer {
    private static final String TOPIC = "market-data";
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        // Simulate market data
        String marketData = "{ \"symbol\": \"AAPL\", \"price\": 150.25, \"volume\": 1000 }";
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, "AAPL", marketData);

        producer.send(record);
        producer.close();
    }
}
```

### 2. Trading Engine

**TradingEngine.java**
```java
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
```

**TradingStrategy.java**
```java
public interface TradingStrategy {
    void execute(MarketData data);
}
```

**SimpleMovingAverageStrategy.java**
```java
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
```

**MarketData.java**
```java
public class MarketData {
    private String symbol;
    private double price;

    public MarketData(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }
}
```

### 3. Order Management System (OMS)

**Order.java**
```java
public class Order {
    private String orderId;
    private String symbol;
    private double price;
    private int quantity;
    private OrderType type;
    private OrderStatus status;

    // Getters and setters
}

enum OrderType {
    MARKET, LIMIT
}

enum OrderStatus {
    NEW, EXECUTED, CANCELLED
}
```

**OrderService.java**
```java
import java.util.HashMap;
import java.util.Map;

public class OrderService {
    private Map<String, Order> orderBook = new HashMap<>();

    public void placeOrder(Order order) {
        order.setStatus(OrderStatus.NEW);
        orderBook.put(order.getOrderId(), order);
        // Logic to send order to market
    }

    public void executeOrder(String orderId) {
        Order order = orderBook.get(orderId);
        if (order != null) {
            order.setStatus(OrderStatus.EXECUTED);
            // Logic to execute the order
        }
    }

    public void cancelOrder(String orderId) {
        Order order = orderBook.get(orderId);
        if (order != null) {
            order.setStatus(OrderStatus.CANCELLED);
            // Logic to cancel the order
        }
    }
}
```

### 4. Backtesting Module

**BacktestingEngine.java**
```java
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
```

### 5. Risk Management

**RiskManagementService.java**
```java
import java.util.HashMap;
import java.util.Map;

public class RiskManagementService {
    private Map<String, Double> exposureLimits = new HashMap<>();

    public void setExposureLimit(String symbol, double limit) {
        exposureLimits.put(symbol, limit);
    }

    public boolean checkRisk(String symbol, double position) {
        Double limit = exposureLimits.get(symbol);
        return limit != null && position <= limit;
    }

    public static void main(String[] args) {
        RiskManagementService riskService = new RiskManagementService();
        riskService.setExposureLimit("AAPL", 10000);

        boolean withinRisk = riskService.checkRisk("AAPL", 5000);
        System.out.println("Within risk limits: " + withinRisk);
    }
}
```

### 6. User Interface

**TradingDashboard.java**
```java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TradingDashboard extends Application {
    @Override
    public void start(Stage primaryStage) {
        Label label = new Label("Trading Dashboard");
        VBox root = new VBox(label);

        Scene scene = new Scene(root, 400, 300);

        primaryStage.setTitle("Trading Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```

### 7. Continuous Integration/Deployment

**Jenkinsfile**
```groovy
pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                script {
                    if (fileExists('pom.xml')) {
                        sh 'mvn clean install'
                    } else if (fileExists('build.gradle')) {
                        sh './gradlew build'
                    }
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    if (fileExists('pom.xml')) {
                        sh 'mvn test'
                    } else if (fileExists('build.gradle')) {
                        sh './gradlew test'
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying application...'
                // Add deployment steps here
            }
        }
    }
}
```

### 8. Docker Deployment

**Dockerfile**
```Dockerfile
FROM openjdk:11-jre-slim
COPY target/trading-platform.jar /app/trading-platform.jar
ENTRYPOINT ["java", "-jar", "/app/trading-platform.jar"]
```

**docker-compose.yml**
```yaml
version: '3.8'
services:
  kafka:
    image: wurstmeister/kafka:latest
    ports:
      - "9092:9092"
    environment:
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181

  zookeeper:
    image: wurstmeister/zookeeper:latest
    ports:
      - "2181:2181"

  trading-platform:
    build: .
    ports:
      - "8080:8080"
    depends_on:
      - kafka
      - zookeeper
```

These code samples provide a foundational structure for each component. To build a fully functional trading platform, you'll need to expand on these examples, integrate them properly, and thoroughly test each part.
