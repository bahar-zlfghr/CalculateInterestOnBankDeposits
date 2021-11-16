package ir.dotin.model.repository;

import ir.dotin.exception.DepositTypeMismatchException;
import ir.dotin.exception.InvalidDepositBalanceException;
import ir.dotin.exception.InvalidDurationInDaysException;
import ir.dotin.model.DepositFactory;
import ir.dotin.model.data.Deposit;
import ir.dotin.model.data.DepositType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Bahar Zolfaghari
 **/
public abstract class DepositRepository {
    private static final List<Deposit> deposits = new ArrayList<>();

    private static Document getDocument() {
        Document document = null;
        try {
            File depositsInfoFile = new File("src/resource/deposits-info");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(depositsInfoFile);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return document;
    }

    public static List<Deposit> getAllDeposits() {
        Document document = getDocument();
        NodeList depositNodes = document.getElementsByTagName("deposit");
        for (int i = 0; i < depositNodes.getLength(); i++) {
            boolean hasError = false;
            Node depositNode = depositNodes.item(i);
            if (depositNode.getNodeType() == Node.ELEMENT_NODE) {
                Element depositElement = (Element) depositNode;
                String customerNumber = depositElement.getElementsByTagName("customerNumber").item(0).getTextContent();
                DepositType depositType = DepositType.getDepositType(depositElement.getElementsByTagName("depositType").item(0).getTextContent());
                if (depositType == null) {
                    try {
                        throw new DepositTypeMismatchException("Error[Deposit" + (i + 1) + "]: The deposit type is not valid!");
                    } catch (DepositTypeMismatchException e) {
                        hasError = true;
                        System.out.println(e.getMessage());
                    }
                }
                String balance = depositElement.getElementsByTagName("depositBalance").item(0).getTextContent();
                BigDecimal depositBalance = new BigDecimal(balance);
                if (depositBalance.compareTo(BigDecimal.ZERO) <= 0) {
                    try {
                        throw new InvalidDepositBalanceException("Error[Deposit" + (i + 1) + "]: The deposit balance is less than zero!");
                    } catch (InvalidDepositBalanceException e) {
                        hasError = true;
                        System.out.println(e.getMessage());
                    }
                }
                int durationInDays = Integer.parseInt(depositElement.getElementsByTagName("durationInDays").item(0).getTextContent());
                if (durationInDays <= 0) {
                    try {
                        throw new InvalidDurationInDaysException("Error[Deposit" + (i + 1) + "]: The duration in days is equal or less than zero!");
                    } catch (InvalidDurationInDaysException e) {
                        hasError = true;
                        System.out.println(e.getMessage());
                    }
                }
                if (!hasError) {
                    try {
                        Method createDeposit = DepositFactory.class.getDeclaredMethod("createDeposit", DepositType.class);
                        Object object = createDeposit.invoke(null, depositType);
                        if (object instanceof Deposit) {
                            Deposit deposit = (Deposit) object;
                            deposit
                                    .setCustomerNumber(customerNumber)
                                    .setDepositBalance(depositBalance)
                                    .setDurationInDays(durationInDays)
                                    .setPayedInterest(deposit.calculateInterest());
                            deposits.add(deposit);
                        }
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return deposits;
    }
}
