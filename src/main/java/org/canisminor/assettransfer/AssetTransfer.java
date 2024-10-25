package org.canisminor.assettransfer;

import com.owlike.genson.Genson;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.*;
import org.hyperledger.fabric.shim.Chaincode;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

@Contract(
        name = "Authorization",
        info = @Info(
                title = "Authorization Management",
                description = "Chaincode of Data Asset X Project",
                version = "0.0.1",
                license = @License(
                        name = "Apache 2.0 License",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"),
                contact = @Contact(
                        email = "canisminor@qq.com",
                        name = "CanisMinor",
                        url = "https://github.com/CanisMinor-1037"
                )
        )
)

@Default
public final class AssetTransfer implements ContractInterface {
    private final Genson genson = new Genson();

    private enum AssetTransferErrors {
        ASSET_NOT_FOUND,
        ASSET_ALREADY_EXISTS
    }

    // User
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public boolean UserExists(final Context ctx, final String id) {
        ChaincodeStub stub = ctx.getStub();
        String userJSON = stub.getStringState(id);

        return (userJSON != null && !userJSON.isEmpty());
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public User CreateUser(final Context ctx, final String id, final String name, final String attribute) {
        ChaincodeStub stub = ctx.getStub();

        if (UserExists(ctx, id)) {
            String errorMessage = String.format("User %s already exists", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_ALREADY_EXISTS.toString());
        }

        User user = new User(id, name, attribute);
        // Use Genson to convert the Asset into string, sort it alphabetically and serialize it into a json string
        String sortedJson = genson.serialize(user);
        stub.putStringState(id, sortedJson);

        stub.setEvent("CreateUser", sortedJson.getBytes(StandardCharsets.UTF_8));
        return user;
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public User ReadUser(final Context ctx, final String id) {
        ChaincodeStub stub = ctx.getStub();
        String userJSON = stub.getStringState(id);

        if (userJSON == null || userJSON.isEmpty()) {
            String errorMessage = String.format("User %s does not exist", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_NOT_FOUND.toString());
        }

        User user = genson.deserialize(userJSON, User.class);
        return user;
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public User UpdateUserAttribute(final Context ctx, final String id, final String attribute) {
        ChaincodeStub stub = ctx.getStub();
        String userJSON = stub.getStringState(id);

        if (userJSON == null || userJSON.isEmpty()) {
            String errorMessage = String.format("User %s does not exist", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_NOT_FOUND.toString());
        }

        User newUser = genson.deserialize(userJSON, User.class);
        newUser.setAttribute(attribute);
        String sortedJson = genson.serialize(newUser);
        stub.putStringState(id, sortedJson);

        stub.setEvent("UpdateUserAttribute", sortedJson.getBytes(StandardCharsets.UTF_8));
        return newUser;
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public String GetAllUser(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();
        List<User> queryResults = new ArrayList<>();

        QueryResultsIterator<KeyValue> results = stub.getStateByRange("User", "User9999999999999999999999999");
        for (KeyValue result : results) {
            User user = genson.deserialize(result.getStringValue(), User.class);
            queryResults.add(user);
        }

        final String reponse = genson.serialize(queryResults);
        return reponse;
    }

    // AttributeApplication
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public boolean AttributeApplicationExists(final Context ctx, final String id) {
        ChaincodeStub stub = ctx.getStub();
        String attributeApplicationJSON = stub.getStringState(id);

        return (attributeApplicationJSON != null && !attributeApplicationJSON.isEmpty());
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public AttributeApplication CreateAttributeApplication(final Context ctx, final String id, final String userId, final String attribute, final int status) {
        ChaincodeStub stub = ctx.getStub();

        if (AttributeApplicationExists(ctx, id)) {
            String errorMessage = String.format("AttributeApplication %s already exists", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_ALREADY_EXISTS.toString());
        }

        if (!UserExists(ctx, userId)) {
            String errorMessage = String.format("User %s does not exist", userId);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_NOT_FOUND.toString());
        }

        AttributeApplication attributeApplication = new AttributeApplication(id, userId, attribute, status);
        String sortedJson = genson.serialize(attributeApplication);
        stub.putStringState(id, sortedJson);

        stub.setEvent("CreateAttributeApplication", sortedJson.getBytes(StandardCharsets.UTF_8));
        return attributeApplication;
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public AttributeApplication ReadAttributeApplication(final Context ctx, final String id) {
        ChaincodeStub stub = ctx.getStub();
        String attributeApplicationJSON = stub.getStringState(id);

        if (attributeApplicationJSON == null || attributeApplicationJSON.isEmpty()) {
            String errorMessage = String.format("AttributeApplication %s does not exist", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_NOT_FOUND.toString());
        }

        AttributeApplication attributeApplication = genson.deserialize(attributeApplicationJSON, AttributeApplication.class);
        return attributeApplication;
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public AttributeApplication UpdateAttributeApplicationStatus(final Context ctx, final String id, final int status) {
        ChaincodeStub stub = ctx.getStub();
        String attributeApplicationJSON = stub.getStringState(id);

        if (attributeApplicationJSON == null || attributeApplicationJSON.isEmpty()) {
            String errorMessage = String.format("AttributeApplication %s does not exist", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_NOT_FOUND.toString());
        }

        AttributeApplication newAttributeApplication = genson.deserialize(attributeApplicationJSON, AttributeApplication.class);
        newAttributeApplication.setStatus(status);
        String sortedJson = genson.serialize(newAttributeApplication);
        stub.putStringState(id, sortedJson);

        stub.setEvent("UpdateAttributeApplicationStatus", sortedJson.getBytes(StandardCharsets.UTF_8));
        return newAttributeApplication;
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public String GetAllAttributeApplication(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();
        List<AttributeApplication> queryResults = new ArrayList<>();

        QueryResultsIterator<KeyValue> results = stub.getStateByRange("AttributeApplication", "AttributeApplication9999999999999999999999999");
        for (KeyValue result : results) {
            AttributeApplication attributeApplication = genson.deserialize(result.getStringValue(), AttributeApplication.class);
            queryResults.add(attributeApplication);
        }

        final String response = genson.serialize(queryResults);
        return response;
    }

    // AttributeItem
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public boolean AttributeItemExists(final Context ctx, final String id) {
        ChaincodeStub stub = ctx.getStub();
        String attributeItemJSON = stub.getStringState(id);

        return (attributeItemJSON != null && !attributeItemJSON.isEmpty());
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public AttributeItem CreateAttributeItem(final Context ctx, final String id, final String content) {
        ChaincodeStub stub = ctx.getStub();

        if (AttributeItemExists(ctx, id)) {
            String errorMessage = String.format("AttributeItem %s already exists", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_ALREADY_EXISTS.toString());
        }

        AttributeItem attributeItem = new AttributeItem(id, content);
        String sortedJson = genson.serialize(attributeItem);
        stub.putStringState(id, sortedJson);

        stub.setEvent("CreateAttributeItem", sortedJson.getBytes(StandardCharsets.UTF_8));
        return attributeItem;
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public AttributeItem ReadAttributeItem(final Context ctx, final String id) {
        ChaincodeStub stub = ctx.getStub();
        String attributeItemJSON = stub.getStringState(id);

        if (attributeItemJSON == null || attributeItemJSON.isEmpty()) {
            String errorMessage = String.format("AttributeItem %s does not exist", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_NOT_FOUND.toString());
        }

        AttributeItem attributeItem = genson.deserialize(attributeItemJSON, AttributeItem.class);
        return attributeItem;
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public AttributeItem DeleteAttributeItem(final Context ctx, final String id) {
        ChaincodeStub stub = ctx.getStub();

        String attributeItemJSON = stub.getStringState(id);

        if (attributeItemJSON == null || attributeItemJSON.isEmpty()) {
            String errorMessage = String.format("AttributeItem %s does not exist", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_NOT_FOUND.toString());
        }

        AttributeItem attributeItem = genson.deserialize(attributeItemJSON, AttributeItem.class);

        stub.delState(id);
        stub.setEvent("DeleteAttributeItem", attributeItemJSON.getBytes(StandardCharsets.UTF_8));
        return attributeItem;
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public String GetAllAttributeItem(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();
        List<AttributeItem> queryResults = new ArrayList<>();

        QueryResultsIterator<KeyValue> results = stub.getStateByRange("AttributeItem", "AttributeItem9999999999999999999999999");
        for (KeyValue result : results) {
            AttributeItem attributeItem = genson.deserialize(result.getStringValue(), AttributeItem.class);
            queryResults.add(attributeItem);
        }

        final String response = genson.serialize(queryResults);
        return response;
    }

}
