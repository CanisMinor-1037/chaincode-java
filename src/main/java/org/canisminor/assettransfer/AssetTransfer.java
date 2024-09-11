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
        name = "DataAsset",
        info = @Info(
                title = "Data Asset Transfer",
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

    // Department
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public boolean DepartmentExists(final Context ctx, final String id) {
        ChaincodeStub stub = ctx.getStub();
        String departmentJSON = stub.getStringState(id);

        return (departmentJSON != null && !departmentJSON.isEmpty());
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public Department CreateDepartment(final Context ctx, final String id, final String name, final String attribute) {
        ChaincodeStub stub = ctx.getStub();

        if (DepartmentExists(ctx, id)) {
            String errorMessage = String.format("Department %s already exists", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_ALREADY_EXISTS.toString());
        }

        Department department = new Department(id, name, attribute);
        // Use Genson to convert the Asset into string, sort it alphabetically and serialize it into a json string
        String sortedJson = genson.serialize(department);
        stub.putStringState(id, sortedJson);

        stub.setEvent("CreateDepartment", sortedJson.getBytes(StandardCharsets.UTF_8));
        return department;
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public Department ReadDepartment(final Context ctx, final String id) {
        ChaincodeStub stub = ctx.getStub();
        String departmentJSON = stub.getStringState(id);

        if (departmentJSON == null || departmentJSON.isEmpty()) {
            String errorMessage = String.format("Department %s does not exist", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_NOT_FOUND.toString());
        }

        Department department = genson.deserialize(departmentJSON, Department.class);
        return department;
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public Department UpdateDepartmentAttribute(final Context ctx, final String id, final String attribute) {
        ChaincodeStub stub = ctx.getStub();
        String departmentJSON = stub.getStringState(id);

        if (departmentJSON == null || departmentJSON.isEmpty()) {
            String errorMessage = String.format("Department %s does not exist", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_NOT_FOUND.toString());
        }

        Department newDepartment = genson.deserialize(departmentJSON, Department.class);
        newDepartment.setAttribute(attribute);
        String sortedJson = genson.serialize(newDepartment);
        stub.putStringState(id, sortedJson);

        stub.setEvent("UpdateDepartmentAttribute", sortedJson.getBytes(StandardCharsets.UTF_8));
        return newDepartment;
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public String GetAllDepartment(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();
        List<Department> queryResults = new ArrayList<>();

        QueryResultsIterator<KeyValue> results = stub.getStateByRange("Department", "Department9999999999999999999999999");
        for (KeyValue result : results) {
            Department department = genson.deserialize(result.getStringValue(), Department.class);
            queryResults.add(department);
        }

        final String reponse = genson.serialize(queryResults);
        return reponse;
    }

    // DataAsset
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public boolean DataAssetExists(final Context ctx, final String id) {
        ChaincodeStub stub = ctx.getStub();
        String dataAssetJSON = stub.getStringState(id);

        return (dataAssetJSON != null && !dataAssetJSON.isEmpty());
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public DataAsset CreateDataAsset(final Context ctx, final String id, final String name, final String ownerId, final String policy, final String location, final String field, final String cid, final String aesKey, final int encType, final String videoPlace, final String videoTime) {
        ChaincodeStub stub = ctx.getStub();

        if (DataAssetExists(ctx, id)) {
            String errorMessage = String.format("DataAsset %s already exists", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_ALREADY_EXISTS.toString());
        }

        if (!DepartmentExists(ctx, ownerId)) {
            String errorMessage = String.format("Department %s does not exist", ownerId);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_NOT_FOUND.toString());
        }

        DataAsset dataAsset = new DataAsset(id, name, ownerId, policy, location, field, cid, aesKey, encType, videoPlace, videoTime);
        String sortedJson = genson.serialize(dataAsset);
        stub.putStringState(id, sortedJson);

        stub.setEvent("CreateDataAsset", sortedJson.getBytes(StandardCharsets.UTF_8));
        return dataAsset;
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public DataAsset ReadDataAsset(final Context ctx, final String id) {
        ChaincodeStub stub = ctx.getStub();
        String dataAssetJSON = stub.getStringState(id);

        if (dataAssetJSON == null || dataAssetJSON.isEmpty()) {
            String errorMessage = String.format("DataAsset %s does not exist", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_NOT_FOUND.toString());
        }

        DataAsset dataAsset = genson.deserialize(dataAssetJSON, DataAsset.class);
        return dataAsset;
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public String GetAllDataAsset(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();
        List<DataAsset> queryResults = new ArrayList<>();

        QueryResultsIterator<KeyValue> results = stub.getStateByRange("DataAsset", "DataAsset9999999999999999999999999");
        for (KeyValue result : results) {
            DataAsset dataAsset = genson.deserialize(result.getStringValue(), DataAsset.class);
            queryResults.add(dataAsset);
        }

        final String response = genson.serialize(queryResults);
        return response;
    }

    // DataAssetOrder
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public boolean DataAssetOrderExists(final Context ctx, final String id) {
        ChaincodeStub stub = ctx.getStub();
        String dataAssetOrderJSON = stub.getStringState(id);

        return (dataAssetOrderJSON != null && !dataAssetOrderJSON.isEmpty());
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public DataAssetOrder CreateDataAssetOrder(final Context ctx, final String id, final String dataAssetId, final String applicantId, final int status) {
        ChaincodeStub stub = ctx.getStub();

        if (DataAssetOrderExists(ctx, id)) {
            String errorMessage = String.format("DataAssetOrder %s already exists", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_ALREADY_EXISTS.toString());
        }

        if (!DataAssetExists(ctx, dataAssetId)) {
            String errorMessage = String.format("DataAsset %s does not exist", dataAssetId);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_NOT_FOUND.toString());
        }

        if (!DepartmentExists(ctx, applicantId)) {
            String errorMessage = String.format("Department %s does not exist", applicantId);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_NOT_FOUND.toString());
        }

        DataAssetOrder dataAssetOrder = new DataAssetOrder(id, dataAssetId, applicantId, status);
        String sortedJson = genson.serialize(dataAssetOrder);
        stub.putStringState(id, sortedJson);

        stub.setEvent("CreateDataAssetOrder", sortedJson.getBytes(StandardCharsets.UTF_8));
        return dataAssetOrder;
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public DataAssetOrder ReadDataAssetOrder(final Context ctx, final String id) {
        ChaincodeStub stub = ctx.getStub();
        String dataAssetOrderJSON = stub.getStringState(id);

        if (dataAssetOrderJSON == null || dataAssetOrderJSON.isEmpty()) {
            String errorMessage = String.format("DataAssetOrder %s does not exist", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_NOT_FOUND.toString());
        }

        DataAssetOrder dataAssetOrder = genson.deserialize(dataAssetOrderJSON, DataAssetOrder.class);
        return dataAssetOrder;
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public DataAssetOrder UpdateDataAssetOrderStatus(final Context ctx, final String id, final int status) {
        ChaincodeStub stub = ctx.getStub();
        String dataAssetOrderJSON = stub.getStringState(id);

        if (dataAssetOrderJSON == null || dataAssetOrderJSON.isEmpty()) {
            String errorMessage = String.format("DataAssetOrder %s does not exist", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_NOT_FOUND.toString());
        }

        DataAssetOrder newDataAssetOrder = genson.deserialize(dataAssetOrderJSON, DataAssetOrder.class);
        newDataAssetOrder.setStatus(status);
        String sortedJSON = genson.serialize(newDataAssetOrder);
        stub.putStringState(id, sortedJSON);

        stub.setEvent("UpdateDataAssetOrderStatus", sortedJSON.getBytes(StandardCharsets.UTF_8));
        return newDataAssetOrder;
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public String GetAllDataAssetOrder(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();
        List<DataAssetOrder> queryResults = new ArrayList<>();

        QueryResultsIterator<KeyValue> results = stub.getStateByRange("DataAssetOrder", "DataAssetOrder99999999999999999999");
        for (KeyValue result : results) {
            DataAssetOrder dataAssetOrder = genson.deserialize(result.getStringValue(), DataAssetOrder.class);
            queryResults.add(dataAssetOrder);
        }

        final String response = genson.serialize(queryResults);
        return response;
    }

    // AttributeApplication
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public boolean AttributeApplicationExists(final Context ctx, final String id) {
        ChaincodeStub stub = ctx.getStub();
        String attributeApplicationJSON = stub.getStringState(id);

        return (attributeApplicationJSON != null && !attributeApplicationJSON.isEmpty());
    }

    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public AttributeApplication CreateAttributeApplication(final Context ctx, final String id, final String departmentId, final String attribute, final int status) {
        ChaincodeStub stub = ctx.getStub();

        if (AttributeApplicationExists(ctx, id)) {
            String errorMessage = String.format("AttributeApplication %s already exists", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_ALREADY_EXISTS.toString());
        }

        if (!DepartmentExists(ctx, departmentId)) {
            String errorMessage = String.format("Department %s does not exist", departmentId);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_NOT_FOUND.toString());
        }

        AttributeApplication attributeApplication = new AttributeApplication(id, departmentId, attribute, status);
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
