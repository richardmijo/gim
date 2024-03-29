ALTER TABLE action DISABLE TRIGGER ALL;

INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (10, '', 'General de acceso', 10, NULL, NULL);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (20, '', 'Inicio', 20, '/home.seam', 10);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (30, '', 'Acceso', 30, '/login.seam', 10);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (40, '', 'Error', 40, '/error.seam', 10);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (50, '', 'Administración', 50, NULL, NULL);

INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (60, '', 'Informática', 60, NULL, 50);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (70, '', 'Acciones', 70, '/common/ActionList.seam', 60);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (80, '', 'Acciones Edicion', 80, '/common/ActionEdit.seam', 60);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (90, '', 'Roles', 90, '/common/RoleList.seam', 60);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (100, '', 'Roles Edicion', 100, '/common/RoleEdit.seam', 60);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (110, '', 'Usuarios', 110, '/common/UserList.seam', 60);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (120, '', 'Usuarios Edicion', 120, '/common/UserEdit.seam', 60);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (130, '', 'Usuarios con sesión activa', 130, '/common/SessionManager.seam', 60);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (140, '', 'Recargar Parámetros Sistema', 140, '/common/UpdateSystemParameters.seam', 60);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (150, '', 'Cambiar Contraseña', 150, '/common/ChangePassword.seam', 50);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (160, '', 'Auditoría', 160, NULL, 50);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (170, '', 'Reportes de Auditoría', 170, '/common/AuditInspector.seam', 160);

INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (200, '', 'SRI', 200, NULL, 50);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (210, '', 'Instituciones', 210, '/income/TaxpayerRecordList.seam', 200);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (220, '', 'Instituciones Edicion', 220, '/income/TaxpayerRecordEdit.seam', 200);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (230, '', 'Establecimientos', 230, '/income/BranchList.seam', 200);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (240, '', 'Establecimientos Edicion', 240, '/income/BranchEdit.seam', 200);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (250, '', 'Tipos de Comprobantes de Venta', 250, '/income/ReceiptTypeList.seam', 200);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (260, '', 'Tipos de Comprobantes de Venta Edicion', 260, '/income/ReceiptTypeEdit.seam', 200);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (270, '', 'Impuestos Fiscales', 270, '/income/TaxList.seam', 200);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (280, '', 'Impuestos Fiscales Edicion', 280, '/income/TaxEdit.seam', 200);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (290, '', 'Generar XML de Facturas', 290, '/income/TaxReporter.seam', 200);

INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (310, '', 'Denominación de Dinero', 310, '/income/MoneyList.seam', 50);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (320, '', 'Denominación de Dinero Edicion', 320, '/income/MoneyEdit.seam', 50);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (330, '', 'Estado de la Obligación Municipal', 330, '/revenue/MunicipalBondStatusList.seam', 50);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (340, '', 'Estado de la Obligación Municipal Edicion', 340, '/revenue/MunicipalBondStatusEdit.seam', 50);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (350, '', 'Periodos de Tiempo', 350, '/revenue/TimePeriodList.seam', 50);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (360, '', 'Periodos de Tiempo Edicion', 360, '/revenue/TimePeriodEdit.seam', 50);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (370, '', 'Instituciones Financieras', 370, '/cadaster/FinancialInstitutionList.seam', 50);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (380, '', 'Instituciones Financieras Edicion', 380, '/cadaster/FinancialInstitutionEdit.seam', 50);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (390, '', 'Actividad Económica', 390, '/commercial/EconomicActivityList.seam', 50);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (400, '', 'Actividad Económica Edicion', 400, '/commercial/EconomicActivityEdit.seam', 50);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (410, '', 'Tipos de Contrato', 410, '/revenue/ContractTypeList.seam', 50);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (420, '', 'Tipos de Contrato Edicion', 420, '/revenue/ContractTypeEdit.seam', 50);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (430, '', 'Finanzas', 430, NULL, NULL);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (440, '', 'Periodo fiscal', 440, '/common/FiscalPeriodList.seam', 430);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (450, '', 'Periodo fiscal Edicion', 450, '/common/FiscalPeriodEdit.seam', 430);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (460, '', 'Plan Contable', 460, '/income/AccountList.seam', 430);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (480, '', 'Créditos de Contribuyente', 480, '/income/CreditNoteList.seam', 430);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (490, '', 'Créditos de Contribuyente Edicion', 490, '/income/CreditNoteEdit.seam', 430);

INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (500, '', 'Reporte de Créditos de Contribuyente', 500, '/income/GenerateCreditNoteReport.seam', 430);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (510, '', 'Reporte de Créditos de Contribuyente Edicion', 510, '/income/GenerateCreditNoteReport.seam', 430);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (520, '', 'Convenios de Pago', 520, '/income/PaymentAgreementList.seam', 430);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (521, NULL, 'Reportes de Emisión Recaudación', 521, '/accounting/FinantialStatement.seam', 430);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (530, '', 'Convenios de Pago Edicion', 530, '/income/PaymentAgreementEdit.seam', 430);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (540, '', 'Tasas de Interés', 540, '/income/InterestRateList.seam', 430);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (550, '', 'Tasas de Interés Edicion', 550, '/income/InterestRateEdit.seam', 430);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (560, '', 'Catastro', 560, NULL, NULL);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (570, '', 'Administrar', 570, NULL, 560);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (580, '', 'Tipos de división Territorial', 580, '/cadaster/TerritorialDivisionTypeList.seam', 570);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (590, '', 'Tipos de división Territorial Edicion', 590, '/cadaster/TerritorialDivisionTypeEdit.seam', 570);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (600, '', 'Divisiones Territoriales', 600, '/cadaster/TerritorialDivisionList.seam', 570);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (610, '', 'Divisiones Territoriales Edicion', 610, '/cadaster/TerritorialDivisionEdit.seam', 570);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (620, '', 'Tipo de Predio', 620, '/cadaster/PropertyTypeList.seam', 570);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (630, '', 'Tipo de Predio Edicion', 630, '/cadaster/PropertyTypeEdit.seam', 570);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (640, '', 'Ubicaciones', 640, '/cadaster/PlaceList.seam', 570);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (650, '', 'Ubicaciones Edicion', 650, '/cadaster/PlaceEdit.seam', 570);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (660, '', 'Barrios', 660, '/cadaster/NeighborhoodList.seam', 570);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (670, '', 'Barrios Edicion', 670, '/cadaster/NeighborhoodEdit.seam', 570);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (680, '', 'Calles', 680, '/cadaster/StreetList.seam', 570);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (690, '', 'Calles Edicion', 690, '/cadaster/StreetEdit.seam', 570);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (700, '', 'Tipos de Uso de Suelo', 700, '/cadaster/LandUseList.seam', 570);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (710, '', 'Tipos de Uso de Suelo Edicion', 710, '/cadaster/LandUseEdit.seam', 570);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (720, '', 'Tipos de vía', 720, '/cadaster/StreetTypeList.seam', 570);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (730, '', 'Tipos de vía Edicion', 730, '/cadaster/StreetTypeEdit.seam', 570);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (740, '', 'Tipos de material de calzada', 740, '/cadaster/StreetMaterialList.seam', 570);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (750, '', 'Tipos de material de calzada Edicion', 750, '/cadaster/StreetMaterialEdit.seam', 570);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (760, '', 'Tipos de material de acera', 760, '/cadaster/SidewalkMaterialList.seam', 570);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (770, '', 'Tipos de material de acera Edicion', 770, '/cadaster/SidewalkMaterialEdit.seam', 570);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (780, '', 'Tipos de material de cerramiento', 780, '/cadaster/FenceMaterialList.seam', 570);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (790, '', 'Tipos de material de cerramiento Edicion', 790, '/cadaster/FenceMaterialEdit.seam', 570);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (800, '', 'Formas de adquisición', 800, '/cadaster/PurchaseTypeList.seam', 570);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (810, '', 'Formas de adquisición Edicion', 810, '/cadaster/PurchaseTypeEdit.seam', 570);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (820, '', 'Posiciones de lote', 820, '/cadaster/LotPositionList.seam', 570);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (830, '', 'Posiciones de lote Edicion', 830, '/cadaster/LotPositionEdit.seam', 570);

INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (920, '', 'Valores de Material de construcción', 920, '/cadaster/BuildingMaterialValueList.seam', 560);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (930, '', 'Valores de Material de construcción Edicion', 930, '/cadaster/BuildingMaterialValueEdit.seam', 560);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (940, '', 'Catastro Manzanero', 940, '/cadaster/BlockList.seam', 560);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (950, '', 'Catastro Manzanero Edicion', 950, '/cadaster/BlockEdit.seam', 560);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (960, '', 'Catastro Predial Urbano', 960, '/cadaster/PropertyList.seam?isUrban=true', 560);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (970, '', 'Catastro Predial Urbano Edicion', 970, '/cadaster/PropertyEdit.seam?isUrban=true', 560);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (980, '', 'Catastro Predial Rústico', 980, '/cadaster/RusticPropertyList.seam?isUrban=false', 560);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (990, '', 'Catastro Predial Rústico Edicion', 990, '/cadaster/RusticPropertyEdit.seam?isUrban=false', 560);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1000, '', 'Pre-Emisión Impuesto de Predio Urbano', 1000, '/cadaster/PreEmissionPropertyTax.seam?isUrban=true', 560);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1010, '', 'Pre-Emisión Impuesto de Predio Rústico', 1010, '/cadaster/PreEmissionPropertyTax.seam?isUrban=false', 560);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1011, NULL, 'Traspasos de Dominio', 1011, '/cadaster/ChangeOwnerProperty.seam', 560);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1012, NULL, 'Agregar Hipotecas', 1012, '/cadaster/DomainEdit.seam', 560);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1013, NULL, 'Historial de Predios', 1013, '/cadaster/HistoryProperty.seam', 560);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1014, NULL, 'Cambiar Nombres de Propietario', 1014, '/cadaster/ChangeOwnerPropertyName.seam', 560);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1015, NULL, 'Editar Solares no Edificados', 1015, '/cadaster/UnbuiltLotEdit.seam', 560);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1016, NULL, 'Solares no Edificados', 1016, '/cadaster/UnbuiltLotList.seam', 560);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1017, NULL, 'Copiar Solares no Edificados', 1017, '/cadaster/CreateCopyUnbuiltLots.seam', 560);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1018, NULL, 'Pre-Emitir Solares no Edificados', 1018, '/cadaster/PreEmissionUnbuiltLot.seam', 560);

INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1040, '', 'Información', 1040, NULL, NULL);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1050, '', 'Estado de Cuenta', 1050, '/revenue/MunicipalBondCondition.seam', 1040);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1060, '', 'Recaudaciones', 1060, NULL, NULL);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1070, '', 'Registro de pagos', 1070, '/income/PaymentEdit.seam', 1060);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1080, '', 'Liquidar compensaciones de Establecimiento', 1080, '/income/BranchCompensationPayment.seam', 1060);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1090, '', 'Apertura de jornada de Recaudación', 1090, '/income/WorkdayList.seam?isFromIncome=true', 1060);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1100, '', 'Apertura de jornada de Recaudación Edicion', 1100, '/income/WorkdayEdit.seam?isFromIncome=true', 1060);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1110, '', 'Cierre de jornada de recaudación', 1110, '/income/ClosingWorkday.seam?isClosingWorkday=true', 1060);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1120, '', 'Cierre de jornada de recaudación boton', 1120, '/income/ClosingWorkday.seam', 1060);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1130, '', 'Aperturar caja', 1130, '/income/TillPermissionEdit.seam', 1060);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1140, '', 'Aperturar caja Edicion', 1140, '/income/TillPermissionEdit.seam', 1060);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1150, '', 'Cerrar caja', 1150, '/income/ClosingTillEdit.seam', 1060);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1160, '', 'Cerrar caja Edicion', 1160, '/income/ClosingTillEdit.seam', 1060);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1170, '', 'Reporte por Puntos de Venta', 1170, '/income/GenerateTillReport.seam', 1060);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1180, '', 'Reportes por cajero', 1180, '/income/GenerateReportByCashier.seam', 1060);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1190, '', 'Reporte de Recaudaciones', 1190, '/income/IncomePaymentReport.seam?globalReport=false&showDetail=true', 1060);

INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1200, '', 'Reporte por Partidas', 1200, '/income/IncomePaymentReport.seam?globalReport=true&showDetail=true', 1060);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1201, NULL, 'Reporte por Cajas', 1201, '/income/TillReportOnlyTotals.seam', 1060);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1210, '', 'Reversar Transacciones', 1210, '/income/DepositEdit.seam', 1060);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1230, '', 'Recaudación sector público', 1230, '/income/PaymentEdit.seam', 1060);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1240, '', 'Recaudación sector público Edicion', 1240, '/income/PrintCompensationAccount.seam', 1060);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1250, '', 'Baja de títulos', 1250, NULL, 1060);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1260, '', 'Baja de títulos Edicion', 1260, NULL, 1060);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1280, '', 'Rentas', 1280, NULL, NULL);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1290, '', 'Administración de Obligaciones municipales', 1290, '/revenue/MunicipalBondManager.seam', 1280);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1300, '', 'Contribuyente', 1300, '/common/ResidentList.seam', 1280);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1310, '', 'Contribuyente Edicion', 1310, '/common/ResidentEdit.seam', 1280);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1320, '', 'Cuentas por Cobrar', 1320, '/revenue/EntryList.seam', 1280);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1330, '', 'Cuentas por Cobrar Edicion', 1330, '/revenue/EntryEdit.seam', 1280);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1340, '', 'Catastro comercial', 1340, '/commercial/BusinessList.seam', 1280);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1350, '', 'Catastro comercial Edicion', 1350, '/commercial/BusinessEdit.seam', 1280);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1360, '', 'Emitir obligaciones municipales', 1360, '/revenue/MunicipalBondEdit.seam', 1280);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1370, '', 'Emitir obligaciones municipales Diferidas', 1370, '/revenue/DeferredMunicipalBondEdit.seam', 1280);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1380, '', 'Ordenes de emisión', 1380, '/revenue/EmissionOrderList.seam?isDispatched=false', 1280);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1381, NULL, 'Ordenes de emisión edit', 1381, '/revenue/EmissionOrderEdit.seam', 1280);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1390, '', 'Definicion de Cuenta Edicion', 1390, '/revenue/EntryDefinitionEdit.seam', 1280);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1391, NULL, 'Buscar propiedades', 1391, '/revenue/SearchProperty.seam', 1280);

INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1400, '', 'Reporte por Emisores', 1400, '/revenue/GenerateEmittedReport.seam', 1280);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1410, '', 'Reporte de Emisión', 1410, '/revenue/RevenueReport.seam?isFromIncome=false&isGlobalReport=true', 1280);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1420, '', 'Certificado de Solvencia', 1420, '/revenue/SolvencyCertificate.seam', 1280);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1421, NULL, 'Ingreso de Exoneraciones', 1421, '/revenue/ExemptionList.seam', 1280);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1422, NULL, 'Ingreso de Exoneraciones edicion', 1422, '/revenue/ExemptionEdit.seam', 1280);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1423, NULL, 'Certificado Traspaso de Dominio', 1423, '/revenue/MunicipalBondConditionByProperty.seam', 1280);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1430, '', 'Sistemas', 1430, NULL, NULL);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1440, '', 'Agua Potable', 1440, NULL, 1430);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1450, '', 'Rutas', 1450, '/waterservice/RouteList.seam', 1440);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1460, '', 'Rutas Edicion', 1460, '/waterservice/RouteEdit.seam', 1440);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1470, '', 'Presupuesto', 1470, '/waterservice/BudgetDTOList.seam', 1440);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1480, '', 'Presupuesto Edicion', 1480, '/waterservice/BudgetEdit.seam', 1440);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1490, '', 'Rubros del presupuesto', 1490, '/waterservice/BudgetEntryList.seam', 1440);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1491, '', 'Registro de Lecturas de Agua', 1491, '/waterservice/RouteRecordReadingNew.seam', 1440);

INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1500, '', 'Rubros del presupuesto Edicion', 1500, '/waterservice/BudgetEntryEdit.seam', 1440);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1510, '', 'Tipos de rubros del presupuesto', 1510, '/waterservice/BudgetEntryTypeList.seam', 1440);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1520, '', 'Tipos de rubros del presupuesto Edicion', 1520, '/waterservice/BudgetEntryTypeEdit.seam', 1440);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1530, '', 'Estados de consumo', 1530, '/waterservice/ConsumptionStateList.seam', 1440);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1540, '', 'Estados de consumo Edicion', 1540, '/waterservice/ConsumptionStateEdit.seam', 1440);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1550, '', 'Estado del Medidor', 1550, '/waterservice/WaterMeterStatusList.seam', 1440);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1560, '', 'Estado del Medidor Edicion', 1560, '/waterservice/WaterMeterStatusEdit.seam', 1440);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1570, '', 'Categorias del servicio de agua', 1570, '/waterservice/WaterSupplyCategoryList.seam', 1440);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1580, '', 'Categorias del servicio de agua Edicion', 1580, '/waterservice/WaterSupplyCategoryEdit.seam', 1440);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1590, '', 'Administración de Servicios', 1590, '/waterservice/WaterSupplyList.seam', 1440);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1600, '', 'Administración de Servicios Edicion', 1600, '/waterservice/WaterSupplyEdit.seam', 1440);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1601, NULL, 'Replanillar Lecturas', 1601, '/waterservice/ChangeRecordReading.seam', 1440);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1602, NULL, 'Orden de Lecturas', 1602, '/waterservice/RouteReadingOrder.seam', 1440);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1603, NULL, 'Preemision de Presupuestos boton', 1603, '/waterservice/PreEmissionBudget.seam', 1440);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1604, NULL, 'Transacciones Agua Potable', 1604, '/waterservice/WaterSupplyOtherTransaction.seam', 1440);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1605, NULL, 'Inspección de Agua Potable boton', 1605, '/waterservice/WaterSupplyInspection.seam', 1440);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1606, NULL, 'Abonados boton', 1606, '/waterservice/WaterSupplyResident.seam', 1440);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1607, NULL, 'Categorias boton', 1607, '/waterservice/WaterSupplyCategory.seam', 1440);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1610, '', 'Cementerios', 1610, NULL, 1430);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1620, '', 'Administración de Cementerios', 1620, '/cementery/CementeryList.seam', 1610);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1630, '', 'Administración de Cementerios Edicion', 1630, '/cementery/CementeryEdit.seam', 1610);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1640, '', 'Alquiler/Renovación Espacios', 1640, '/cementery/UnitDeathList.seam', 1610);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1660, '', 'Registro de defunciones', 1660, '/cementery/DeathList.seam', 1610);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1670, '', 'Registro de defunciones Edicion', 1670, '/cementery/DeathEdit.seam', 1610);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1680, '', 'Coactivas', 1680, NULL, 1430);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1690, '', 'Notificaciones', 1690, '/coercive/NotificationList.seam', 1680);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1691, NULL, 'Obligaciones Pendientes por Notificacion', 1691, '/coercive/DetailPendingMunicipalBonds.seam', 1680);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1700, '', 'Notificaciones Edicion', 1700, '/coercive/NotificationEdit.seam', 1680);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1710, '', 'Obligaciones Vencidas', 1710, '/coercive/ResidentWithMunicipalBondOutOfDateList.seam', 1680);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1730, '', 'Reporte de Recuperacion de Cartera Vencida', 1730, '/coercive/GenerateNotificationReport.seam', 1680);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1740, '', 'Mercados', 1740, NULL, 1430);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1750, '', 'Tipo de producto', 1750, '/market/ProductTypeList.seam', 1740);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1760, '', 'Tipo de producto Edicion', 1760, '/market/ProductTypeEdit.seam', 1740);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1770, '', 'Tipo de Local', 1770, '/market/StandTypeList.seam', 1740);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1780, '', 'Tipo de Local Edicion', 1780, '/market/StandTypeEdit.seam', 1740);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1790, '', 'Mercados', 1790, '/market/MarketList.seam', 1740);

INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1800, '', 'Mercado Edicion', 1800, '/market/MarketEdit.seam', 1740);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1810, '', 'Parqueadero', 1810, NULL, 1430);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1820, '', 'Administración de Parqueaderos', 1820, '/parking/ParkingLotList.seam', 1810);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1830, '', 'Administración de Parqueaderos Edicion', 1830, '/parking/ParkingLotEdit.seam', 1810);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1840, '', 'Arriendo de Parqueaderos', 1840, '/parking/ParkingRentList.seam', 1810);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1850, '', 'Arriendo de Parqueaderos Edicion', 1850, '/parking/ParkingRentEdit.seam', 1810);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1860, '', 'Emisión de Ticket', 1860, '/parking/TicketEmission.seam', 1810);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1870, '', 'Recepción de Ticket', 1870, '/parking/TicketReceipt.seam', 1810);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1880, '', 'Administración de Tickets', 1880, '/parking/TicketList.seam', 1810);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1890, '', 'Informe de Recaudaciones', 1890, '/parking/TicketSummaryList.seam', 1810);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1900, '', 'Emisión de Reporte de Recaudaciones', 1900, '/parking/TicketSummaryList.seam?isOnlyReport=false', 1810);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1910, '', 'Regulación y Control', 1910, NULL, 1430);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1920, '', 'Espacios público', 1920, '/rental/SpaceList.seam', 1910);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1930, '', 'Espacios público Edicion', 1930, '/rental/SpaceEdit.seam?sort=contract.expirationDate&dir=asc&logic=and', 1910);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1940, '', 'Tipo de espacio público', 1940, '/rental/SpaceTypeList.seam?logic=and', 1910);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (1950, '', 'Tipo de espacio público Edicion', 1950, '/rental/SpaceTypeEdit.seam?logic=and', 1910);


INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2001, NULL, 'Catastro de Lecturas', 1619, '/waterservice/CadastreTakingReading.seam', 1440);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2002, NULL, 'Obligaciones Municipales por Estado', 1500, '/waterservice/MunicipalBondsWaterIndicator.seam', 1440);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2003, NULL, 'Consumo Fix', 2003, '/waterservice/WaterSupplyEditFix.seam', 1440);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2004, NULL, 'Pre-Emisiones realizadas agua potable', 2004, '/waterservice/RouteEmited.seam', 1440);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2005, NULL, 'Reporte de Indicadores Agua Potable', 2005, '/waterservice/WaterConsumptionIndicator.seam', 1440);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2006, NULL, 'Puestos', 2006, '/market/StandList.seam', 1740);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2007, NULL, 'Reporte de pagos - Umapal', 2007, '/waterservice/RouteDebtSearch.seam', 1440);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2008, NULL, 'Error en Emisiones - UMAPAL', 2008, '/waterservice/WrongWaterEmission.seam', 1440);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2009, NULL, 'Emision de Mercados', 2009, '/market/MarketEmissionList.seam', 1740);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2010, NULL, 'Arrendamiento de MeRcados', 2010, '/market/MarketRent.seam', 1740);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2011, NULL, 'Facturar por Servicio de Agua Potable', 2011, '/waterservice/InvoiceByWaterService.seam', 1440);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2012, NULL, 'Marca de Vehiculos', 2012, '/revenue/VehicleMakerList.seam', 50);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2013, NULL, 'Editar Marca de Vehiculo', 2013, '/revenue/VehicleMakerEdit.seam', 50);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2014, NULL, 'Impresion reporte de indicadores', 321321, '/waterservice/report/WaterConsumptionIndicatorReport.seam', NULL);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2015, NULL, 'Editar Local', 2015, '/market/StandEdit.seam', 1740);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2016, NULL, 'Derechos y Acciones', 2016, '/cadaster/RightsTransferList.seam', 560);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2017, NULL, 'Editar Derechos y Acciones', 2017, '/cadaster/RightsTransferEdit.seam', 560);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2018, NULL, 'Obras', 2018, '/cadaster/WorkDealList.seam', 560);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2019, NULL, 'Editar Obras', 2019, '/cadaster/WorkDealEdit.seam', 560);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2020, NULL, 'Reporte de modificaciones en predios', 2020, '/cadaster/DomainChanges.seam', 560);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2021, NULL, 'Pre-Emisión puestos de mercado', 2021, '/market/PreEmitMarketStand.seam', 1740);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2022, NULL, 'Encargados del parqueadero', 2022, '/parking/ParkingLotJournalEdit.seam', 1810);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2023, NULL, 'Editar Ticket', 2023, '/parking/TicketEdit.seam', 1810);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2024, NULL, 'Alquilar espacio publicitario', 2024, '/rental/RentSpace.seam', 1910);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2025, NULL, 'Pre-Emitir espacio publicitario', 2025, '/rental/PreEmitRentSpace.seam', 1910);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2026, NULL, 'Exoneraciones solares no edificados', 2026, '/revenue/LandExemptionList.seam', 1280);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2027, NULL, 'Editar Exoneraciones solares no edificados', 2027, '/revenue/LandExemptionEdit.seam', 1280);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2028, NULL, 'Pago Cuotas', 2028, '/waterservice/BudgetFeeEdit.seam', 1440);

INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2029, NULL, 'Ingresar Codigo de Agrupacion', 2029, '/revenue/GroupingCodeEdit.seam', 1280);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2030, NULL, 'Cartera Vencida', 2030, '/coercive/DetailedDuePortfolio.seam', 1680);


INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2031, NULL, 'Concesiones', 2031, '', 1430);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2032, NULL, 'Clasificaciones', 2032, '/concession/ConcessionClasificationList.seam', 2031);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2033, NULL, 'Clasification Edicion', 2033, '/concession/ConcessionClasificationEdit.seam', 2031);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2034, NULL, 'Grupos', 2034, '/concession/ConcessionGroupList.seam', 2031);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2035, NULL, 'Grupo Edicion', 2035, '/concession/ConcessionGroupEdit.seam', 2031);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2036, NULL, 'Gruop DTO', 2036, '/concession/ConcessionGroupListDTO.seam', 2031);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2037, NULL, 'Group DTO Edit', 2037, '/concession/ConcessionGroupDTO.seam', 2031);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2038, NULL, 'Tipo de Lugar', 2038, '/concession/ConcessionPlaceTypeList.seam', 2031);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2039, NULL, 'Tipo de Lugar Edicion', 2039, '/concession/ConcessionPlaceTypeEdit.seam', 2031);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2040, NULL, 'Emision', 2040, '/concession/ConcessionGroupListEmission.seam', 2031);
INSERT INTO action (id, icon, name, priority, url, parent_id) VALUES (2041, NULL, 'Emision Edicion', 2041, '/concession/ConcessionGroupEmission.seam', 2031);

ALTER TABLE action ENABLE TRIGGER ALL;
