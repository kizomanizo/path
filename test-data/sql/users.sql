-- Copyright © 2013 VillageReach.  All Rights Reserved.  This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
-- If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.

INSERT INTO roles
 (name, adminRole, description) VALUES
 ('Store In-Charge', 'f', ''),
 ('LMU', 'f', ''),
 ('LMU In-Charge', 't', ''),
 ('FacilityHead', 'f', ''),
 ('Medical-Officer', 'f', '');

INSERT INTO role_rights
  (roleId, rightName) VALUES
  ((select id from roles where name='Store In-Charge'), 'VIEW_REQUISITION'),
  ((select id from roles where name='Store In-Charge'), 'CREATE_REQUISITION'),
  ((select id from roles where name='Medical-Officer'), 'VIEW_REQUISITION'),
  ((select id from roles where name='Medical-Officer'), 'APPROVE_REQUISITION'),
  ((select id from roles where name='FacilityHead'), 'AUTHORIZE_REQUISITION'),
  ((select id from roles where name='FacilityHead'), 'VIEW_REQUISITION'),
  ((select id from roles where name='LMU'), 'VIEW_REQUISITION'),
  ((select id from roles where name='LMU'), 'APPROVE_REQUISITION'),
  ((select id from roles where name='LMU In-Charge'), 'CONVERT_TO_ORDER'),
  ((select id from roles where name='LMU In-Charge'), 'VIEW_ORDER');

INSERT INTO VENDORS (name, active) VALUES ('commTrack', true);

INSERT INTO users
  (id, userName, password,vendorId, facilityId, firstName, lastName, email, active) VALUES
  (200, 'StoreInCharge', 'Agismyf1Whs0fxr1FFfK8cs3qisVJ1qMs3yuMLDTeEcZEGzstjiswaaUsQNQTIKk1U5JRzrDbPLCzCO1isvB5YGaEQieie',(SELECT id FROM vendors WHERE name = 'openLmis'), (SELECT id FROM facilities WHERE code = 'F10'), 'Fatima', 'Doe', 'Fatima_Doe@openlmis.com', true),
  (300, 'FacilityHead', 'Agismyf1Whs0fxr1FFfK8cs3qisVJ1qMs3yuMLDTeEcZEGzstjiswaaUsQNQTIKk1U5JRzrDbPLCzCO1isvB5YGaEQieie',(SELECT id FROM vendors WHERE name = 'openLmis') ,(SELECT id FROM facilities WHERE code = 'F10'), 'Jane', 'Doe', 'Jane_Doe@openlmis.com', true),
  (400, 'MedicalOfficer', 'Agismyf1Whs0fxr1FFfK8cs3qisVJ1qMs3yuMLDTeEcZEGzstjiswaaUsQNQTIKk1U5JRzrDbPLCzCO1isvB5YGaEQieie',(SELECT id FROM vendors WHERE name = 'openLmis'), (SELECT id FROM facilities WHERE code = 'F10'), 'John', 'Doe', 'Joh_Doe@openlmis.com', true),
  (500, 'lmu', 'Agismyf1Whs0fxr1FFfK8cs3qisVJ1qMs3yuMLDTeEcZEGzstjiswaaUsQNQTIKk1U5JRzrDbPLCzCO1isvB5YGaEQieie',(SELECT id FROM vendors WHERE name = 'openLmis'), (SELECT id FROM facilities WHERE code = 'F10'), 'Frank', 'Doe', 'Frank_Doe@openlmis.com', true),
  (600, 'lmuincharge', 'Agismyf1Whs0fxr1FFfK8cs3qisVJ1qMs3yuMLDTeEcZEGzstjiswaaUsQNQTIKk1U5JRzrDbPLCzCO1isvB5YGaEQieie',(SELECT id FROM vendors WHERE name = 'openLmis'), (SELECT id FROM facilities WHERE code = 'F10'), 'Frank', 'Doe', 'Jake_Doe@openlmis.com', true);

INSERT INTO USERS
  (id, userName, password, facilityId, firstName, lastName, vendorId ,active) VALUES
  (700, 'commTrack', 'Agismyf1Whs0fxr1FFfK8cs3qisVJ1qMs3yuMLDTeEcZEGzstjiswaaUsQNQTIKk1U5JRzrDbPLCzCO1isvB5YGaEQieie', (SELECT id FROM facilities WHERE code = 'F10'), 'CommTrack', 'Doe',(SELECT id from vendors where name='commTrack'), true);


INSERT INTO supervisory_nodes
  (parentId, facilityId, name, code) VALUES
  (null, (SELECT id FROM facilities WHERE code = 'F10'), 'Node 1', 'N1');

 INSERT INTO supervisory_nodes
  (parentId, facilityId, name, code) VALUES
  ((select id from  supervisory_nodes where code ='N1'), (SELECT id FROM facilities WHERE code = 'F11'), 'Node 2', 'N2');

INSERT INTO role_assignments
  (userId, roleId, programId, supervisoryNodeId) VALUES
  (200, (SELECT id FROM roles WHERE name = 'Store In-Charge'), 3, null),
  (200, (SELECT id FROM roles WHERE name = 'Store In-Charge'), 2, null),
  (300, (SELECT id FROM roles WHERE name = 'FacilityHead'), 3, (SELECT id from supervisory_nodes WHERE code = 'N2')),
  (300, (SELECT id FROM roles WHERE name = 'FacilityHead'), 2, (SELECT id from supervisory_nodes WHERE code = 'N2')),
  (400, (SELECT id FROM roles WHERE name = 'Medical-Officer'), 3, (SELECT id from supervisory_nodes WHERE code = 'N2')),
  (400, (SELECT id FROM roles WHERE name = 'Medical-Officer'), 2, (SELECT id from supervisory_nodes WHERE code = 'N2')),
  (500, (SELECT id FROM roles WHERE name = 'LMU'), 3, (SELECT id from supervisory_nodes WHERE code = 'N1')),
  (500, (SELECT id FROM roles WHERE name = 'LMU'), 2, (SELECT id from supervisory_nodes WHERE code = 'N1')),
  (600, (SELECT id FROM roles WHERE name = 'LMU In-Charge'), 3, (SELECT id from supervisory_nodes WHERE code = 'N1')),
  (600, (SELECT id FROM roles WHERE name = 'LMU In-Charge'), 2, (SELECT id from supervisory_nodes WHERE code = 'N1'));

