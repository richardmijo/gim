update IdentityGenerator set value = (select max(id) + 1 from Attachment) where name = 'Attachment';
update IdentityGenerator set value = (select max(id) + 1 from Block) where name = 'Block';
update IdentityGenerator set value = (select max(id) + 1 from BlockLimit) where name = 'BlockLimit';
update IdentityGenerator set value = (select max(id) + 1 from Building) where name = 'Building';
update IdentityGenerator set value = (select max(id) + 1 from Cementery) where name = 'Cementery';
update IdentityGenerator set value = (select max(id) + 1 from Charge) where name = 'Charge';
update IdentityGenerator set value = (select max(id) + 1 from ConsumptionState) where name = 'ConsumptionState';
update IdentityGenerator set value = (select max(id) + 1 from Contract) where name = 'Contract';
update IdentityGenerator set value = (select max(id) + 1 from ContractType) where name = 'ContractType';
update IdentityGenerator set value = (select max(id) + 1 from Delegate) where name = 'Delegate';
update IdentityGenerator set value = (select max(id) + 1 from Domain) where name = 'Domain';
update IdentityGenerator set value = (select max(id) + 1 from Entry) where name = 'Entry';
update IdentityGenerator set value = (select max(id) + 1 from EntryDefinition) where name = 'EntryDefinition';
update IdentityGenerator set value = (select max(id) + 1 from EntryStructure) where name = 'EntryStructure';
update IdentityGenerator set value = (select max(id) + 1 from EntryTypeIncome) where name = 'EntryTypeIncome';
update IdentityGenerator set value = (select max(id) + 1 from FenceMaterial) where name = 'FenceMaterial';
update IdentityGenerator set value = (select max(id) + 1 from FiscalPeriod) where name = 'FiscalPeriod';
update IdentityGenerator set value = (select max(id) + 1 from InterestRate) where name = 'InterestRate';
update IdentityGenerator set value = (select max(id) + 1 from LandUse) where name = 'LandUse';
update IdentityGenerator set value = (select max(id) + 1 from Location) where name = 'Location';
update IdentityGenerator set value = (select max(id) + 1 from LotPosition) where name = 'LotPosition';
update IdentityGenerator set value = (select max(id) + 1 from MunicipalBondStatus) where name = 'MunicipalBondStatus';
update IdentityGenerator set value = (select max(id) + 1 from Neighborhood) where name = 'Neighborhood';
update IdentityGenerator set value = (select max(id) + 1 from NotificationTaskType) where name = 'NotificationTaskType';
update IdentityGenerator set value = (select max(id) + 1 from Property) where name = 'Property';
update IdentityGenerator set value = (select max(id) + 1 from PropertyLandUse) where name = 'PropertyLandUse';
update IdentityGenerator set value = (select max(id) + 1 from PropertyType) where name = 'PropertyType';
update IdentityGenerator set value = (select max(id) + 1 from PurchaseType) where name = 'PurchaseType';
update IdentityGenerator set value = (select max(id) + 1 from Resident) where name = 'Resident';
update IdentityGenerator set value = (select max(id) + 1 from Route) where name = 'Route';
update IdentityGenerator set value = (select max(id) + 1 from SidewalkMaterial) where name = 'SidewalkMaterial';
update IdentityGenerator set value = (select max(id) + 1 from SpaceType) where name = 'SpaceType';
update IdentityGenerator set value = (select max(id) + 1 from Street) where name = 'Street';
update IdentityGenerator set value = (select max(id) + 1 from StreetMaterial) where name = 'StreetMaterial';
update IdentityGenerator set value = (select max(id) + 1 from StreetType) where name = 'StreetType';
update IdentityGenerator set value = (select max(id) + 1 from TerritorialDivision) where name = 'TerritorialDivision';
update IdentityGenerator set value = (select max(id) + 1 from TerritorialDivisionType) where name = 'TerritorialDivisionType';
update IdentityGenerator set value = (select max(id) + 1 from TimePeriod) where name = 'TimePeriod';
update IdentityGenerator set value = (select max(id) + 1 from UnitType) where name = 'UnitType';
update IdentityGenerator set value = (select max(id) + 1 from WaterMeter) where name = 'WaterMeter';
update IdentityGenerator set value = (select max(id) + 1 from WaterMeterStatus) where name = 'WaterMeterStatus';
update IdentityGenerator set value = (select max(id) + 1 from WaterSupply) where name = 'WaterSupply';
update IdentityGenerator set value = (select max(id) + 1 from WaterSupplyCategory) where name = 'WaterSupplyCategory';
update IdentityGenerator set value = (select max(id) + 1 from Money) where name = 'Money';
update IdentityGenerator set value = (select max(id) + 1 from Action) where name = 'Action';
update IdentityGenerator set value = (select max(id) + 1 from Branch) where name = 'Branch';
update IdentityGenerator set value = (select max(id) + 1 from Till) where name = 'Till';
update IdentityGenerator set value = (select max(id) + 1 from Permission) where name = 'Permission';
update IdentityGenerator set value = (select max(id) + 1 from Role) where name = 'Role';
update IdentityGenerator set value = (select max(id) + 1 from Workday) where name = 'Workday';
update IdentityGenerator set value = (select max(id) + 1 from TillPermission) where name = 'TillPermission';
update IdentityGenerator set value = (select max(id) + 1 from SessionRecord) where name = 'SessionRecord';
update IdentityGenerator set value = (select max(id) + 1 from Account) where name = 'Account';
update IdentityGenerator set value = (select max(id) + 1 from _User) where name = '_User';
