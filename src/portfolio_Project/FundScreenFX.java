package portfolio_Project;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class FundScreenFX extends Application {

	public void start(Stage primaryStage) {

		Portfolio portfolio = new Portfolio();
		TextArea textArea = new TextArea();
		Button btKeepByTicker = new Button("Ticker");
		Button btKeepByHoldingSize = new Button("Holding Size");
		Button btKeepByMinimumInvestment = new Button(" Min Invest");
		Button btKeepByValue = new Button("Value");
		Button btKeepByLocation = new Button("Location");

		Button btExcludeByTicker = new Button("Ticker");
		Button btExcludeByHoldingSize = new Button("Holding Size");
		Button btExcludeByMinimumInvestment = new Button("Min Invest");
		Button btExcludeByValue = new Button("Value");
		Button btExcludeByLocation = new Button("Location");

		HBox hBoxTopLeft = new HBox(new Label("Keep by"), btKeepByTicker, btKeepByHoldingSize,
				btKeepByMinimumInvestment, btKeepByValue, btKeepByLocation);
		hBoxTopLeft.setSpacing(10);
		hBoxTopLeft.setAlignment(Pos.CENTER);
		hBoxTopLeft.setPadding(new Insets(5, 5, 5, 5));

		HBox hBoxTopRight = new HBox(new Label("Exclude by"), btExcludeByTicker, btExcludeByHoldingSize,
				btExcludeByMinimumInvestment, btExcludeByValue, btExcludeByLocation);
		hBoxTopRight.setSpacing(10);
		hBoxTopRight.setAlignment(Pos.CENTER);
		hBoxTopRight.setPadding(new Insets(5, 5, 5, 5));

		GridPane gridPaneTop = new GridPane();
		gridPaneTop.add(hBoxTopLeft, 0, 0);
		gridPaneTop.add(hBoxTopRight, 1, 0);

		Button btResetPortfolio = new Button("Reset Portfolio");
		TextField txTicker = new TextField();
		txTicker.setPrefWidth(50);
		TextField txFloor = new TextField();
		txFloor.setPrefWidth(50);
		TextField txCeiling = new TextField();
		txCeiling.setPrefWidth(50);

		ChoiceBox<String> cbdomicile = new ChoiceBox<String>();
		cbdomicile.getItems().addAll("Domestic", "International", "Global");
		cbdomicile.getSelectionModel().select(0);
		Button btClearAnswers = new Button("Clear");
		Button btnExit = new Button("Exit");

		HBox hBoxBottom = new HBox(btResetPortfolio, new Label("Ticker"), txTicker, new Label("Floor"),
				txFloor, new Label("Ceiling"), txCeiling, new Label("Location"), cbdomicile, btClearAnswers, btnExit);
		hBoxBottom.setSpacing(10);
		hBoxBottom.setAlignment(Pos.CENTER);
		hBoxBottom.setPadding(new Insets(5, 5, 5, 5));

		BorderPane borderPane = new BorderPane();
		borderPane.setTop(gridPaneTop);
		borderPane.setBottom(hBoxBottom);
		borderPane.setCenter(textArea);

		btResetPortfolio.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				portfolio.refresh();
				String output = "Current Portfolio\n";
				for (int i = 0; i < portfolio.size(); i++) {
					output += "[" + i + "] " + portfolio.get(i) + "\n";
				}
				textArea.setText(output);
			}
		});

		btKeepByTicker.setOnAction(e -> {
		    String ticker = txTicker.getText().toUpperCase();
		    portfolio.removeIf(fund -> !fund.getTicker().equals(ticker)); // Keep only matching ticker
		    updateTextArea(textArea, portfolio);
		});

		btKeepByValue.setOnAction(e -> {
		    try {
		        double floor = Double.parseDouble(txFloor.getText());
		        double ceiling = Double.parseDouble(txCeiling.getText());

		        // Remove funds that are NOT in the range
		        portfolio.removeIf(fund -> fund.getValueMeasure() < floor || fund.getValueMeasure() > ceiling);

		        updateTextArea(textArea, portfolio); // Refresh portfolio
		    } catch (NumberFormatException ex) {
		        textArea.setText("Invalid input! Please enter numeric values for Floor and Ceiling.");
		    }
		});

		btKeepByLocation.setOnAction(e -> {
		    MutualFund.MARKET mkt = MutualFund.MARKET.valueOf(cbdomicile.getValue().toUpperCase());
		    
		    // Remove all funds that do not match the selected location
		    for (int i = portfolio.size() - 1; i >= 0; i--) {
		        if (portfolio.get(i).getDomicile() != mkt) {
		            portfolio.remove(i);
		        }
		    }
		    
		    // Update the display
		    updateTextArea(textArea, portfolio);
		});

		
		// Keep only funds within a specified holding size range
		btKeepByHoldingSize.setOnAction(e -> {
		    double floor = Double.parseDouble(txFloor.getText());
		    double ceiling = Double.parseDouble(txCeiling.getText());

		    portfolio.removeIf(fund -> fund.getAvgHoldingSize() < floor || fund.getAvgHoldingSize() > ceiling);

		    // Update the display
		    updateTextArea(textArea, portfolio);
		});

		
		// Keep only funds within a specified minimum investment range
		btKeepByMinimumInvestment.setOnAction(e -> {
		    double floor = Double.parseDouble(txFloor.getText());
		    double ceiling = Double.parseDouble(txCeiling.getText());

		    portfolio.removeIf(fund -> fund.getMinimumInvestment() < floor || fund.getMinimumInvestment() > ceiling);

		    // Update the display
		    updateTextArea(textArea, portfolio);
		});


		btExcludeByTicker.setOnAction(e -> {
		    String ticker = txTicker.getText().toUpperCase();
		    portfolio.removeIf(fund -> fund.getTicker().equals(ticker)); // Remove matching ticker
		    updateTextArea(textArea, portfolio);
		});

		// Exclude funds within a specified value range
		btExcludeByValue.setOnAction(e -> {
		    double floor = Double.parseDouble(txFloor.getText());
		    double ceiling = Double.parseDouble(txCeiling.getText());

		    portfolio.removeIf(fund -> fund.getValueMeasure() >= floor && fund.getValueMeasure() <= ceiling);

		    // Update the display
		    updateTextArea(textArea, portfolio);
		});

		
		// Exclude funds based on location
		btExcludeByLocation.setOnAction(e -> {
		    MutualFund.MARKET mkt = MutualFund.MARKET.valueOf(cbdomicile.getValue().toUpperCase());

		    portfolio.removeIf(fund -> fund.getDomicile() == mkt);

		    // Update the display
		    updateTextArea(textArea, portfolio);
		});

		
		// Exclude funds within a specified holding size range
		btExcludeByHoldingSize.setOnAction(e -> {
		    double floor = Double.parseDouble(txFloor.getText());
		    double ceiling = Double.parseDouble(txCeiling.getText());

		    portfolio.removeIf(fund -> fund.getAvgHoldingSize() >= floor && fund.getAvgHoldingSize() <= ceiling);

		    // Update the display
		    updateTextArea(textArea, portfolio);
		});
		
		// Exclude funds within a specified minimum investment range
		btExcludeByMinimumInvestment.setOnAction(e -> {
		    double floor = Double.parseDouble(txFloor.getText());
		    double ceiling = Double.parseDouble(txCeiling.getText());

		    portfolio.removeIf(fund -> fund.getMinimumInvestment() >= floor && fund.getMinimumInvestment() <= ceiling);

		    // Update the display
		    updateTextArea(textArea, portfolio);
		});
		
		btClearAnswers.setOnAction(new EventHandler<ActionEvent>() {
			@Override // Override the handle method
			public void handle(ActionEvent e) {
				textArea.setText("");
			}
		});

		btnExit.setOnAction(e -> {
			System.exit(0);
		});

		// Create a scene and place it in the stage
		Scene scene = new Scene(borderPane, 900, 350);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Image myIcon = new Image(getClass().getResourceAsStream("SP.png"));
		primaryStage.getIcons().add(myIcon);
		primaryStage.setTitle("Fund Screen");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	  private void updateTextArea(TextArea textArea, Portfolio portfolio) {
	        StringBuilder output = new StringBuilder("Current Portfolio\n");
	        for (int i = 0; i < portfolio.size(); i++) {
	            output.append("[").append(i).append("] ").append(portfolio.get(i)).append("\n");
	        }
	        textArea.setText(output.toString());
	    }

	
	public static void main(String[] args) {
		launch(args);
	}
}