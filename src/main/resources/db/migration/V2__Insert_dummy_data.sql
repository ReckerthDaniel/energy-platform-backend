-- USERS
INSERT INTO app_user(id, address, birthday, fullname, password, role, username)
VALUES(decode(replace('8d9dce08-4b9f-4f40-b5c2-f9e282fbc664'::text,'-',''),'hex'),
       'Cluj',
       '1999-07-04',
       'Daniel Reckerth',
       '$2a$12$QGZKDydu89vethbKl0hLxOmNlurWVbFRNVoh6MC0IDE/f3aHg8kU6',
       'ROLE_ADMIN',
       'rdaniel');

INSERT INTO app_user(id, address, birthday, fullname, password, role, username)
VALUES(decode(replace('ea66c8dd-f0a7-4bd9-8ad3-c3a8b9beb347'::text,'-',''),'hex'),
       'Sibiu',
       '1978-05-20',
       'John Adam',
       '$2a$12$R2mVcyMXAMOr/fdWdDrnq.Sn6806pINXjoWjQnxXlpTgwpoett8dm',
       'ROLE_CLIENT',
       'john');

-- DEVICES
INSERT INTO device(id, avgec, device_description, location, maxec, sensor_description, sensor_max_value, user_id)
VALUES(decode(replace('14047166-53fe-4ba0-9290-c16554229826'::text,'-',''),'hex'),
       15.36,
       'Dishwasher',
       'Kitchen',
       32.12,
       'Electrical',
       35.0,
       decode(replace('ea66c8dd-f0a7-4bd9-8ad3-c3a8b9beb347'::text,'-',''),'hex'));

INSERT INTO device(id, avgec, device_description, location, maxec, sensor_description, sensor_max_value, user_id)
VALUES(decode(replace('dc336d44-f0c2-413f-a6c2-3b3f99100b13'::text,'-',''),'hex'),
       1.75,
       'Smartphone',
       'At user',
       5.7,
       'Electrical',
       6.1,
       decode(replace('ea66c8dd-f0a7-4bd9-8ad3-c3a8b9beb347'::text,'-',''),'hex'));

-- MEASUREMENTS: Dishwasher
INSERT INTO measurement(id, ec, timestamp, device_id)
VALUES(decode(replace('ede600b1-8379-4649-898e-46767cb5470a'::text,'-',''),'hex'),
       17.3,
       '2021-11-18 14:57:13',
       decode(replace('14047166-53fe-4ba0-9290-c16554229826'::text,'-',''),'hex')
      );

INSERT INTO measurement(id, ec, timestamp, device_id)
VALUES(decode(replace('e7169e1b-d8ee-48fd-bb29-32c62df8c819'::text,'-',''),'hex'),
       11.36,
       '2021-11-18 10:20:00',
       decode(replace('14047166-53fe-4ba0-9290-c16554229826'::text,'-',''),'hex')
      );

INSERT INTO measurement(id, ec, timestamp, device_id)
VALUES(decode(replace('96f7a2c0-11fd-4a62-b272-a94e62825af3'::text,'-',''),'hex'),
       3.48,
       '2021-11-18 05:27:00',
       decode(replace('14047166-53fe-4ba0-9290-c16554229826'::text,'-',''),'hex')
      );

INSERT INTO measurement(id, ec, timestamp, device_id)
VALUES(decode(replace('748965d6-bf33-4467-bf4d-0ef565790168'::text,'-',''),'hex'),
       15.89,
       '2021-11-18 18:15:00',
       decode(replace('14047166-53fe-4ba0-9290-c16554229826'::text,'-',''),'hex')
      );

INSERT INTO measurement(id, ec, timestamp, device_id)
VALUES(decode(replace('4cec52fe-c158-4895-95fd-9b8cf46a3a0a'::text,'-',''),'hex'),
       21.75,
       '2021-11-18 19:00:00',
       decode(replace('14047166-53fe-4ba0-9290-c16554229826'::text,'-',''),'hex')
      );

-- MEASUREMENTS: Smartphone
INSERT INTO measurement(id, ec, timestamp, device_id)
VALUES(decode(replace('72181556-110c-4575-ba17-de544ea9b687'::text,'-',''),'hex'),
       1.2,
       '2021-11-20 04:00:00',
       decode(replace('dc336d44-f0c2-413f-a6c2-3b3f99100b13'::text,'-',''),'hex')
      );

INSERT INTO measurement(id, ec, timestamp, device_id)
VALUES(decode(replace('f7e63b50-55e9-45e4-b129-13d3e3bc385e'::text,'-',''),'hex'),
       2.3,
       '2021-11-20 10:12:00',
       decode(replace('dc336d44-f0c2-413f-a6c2-3b3f99100b13'::text,'-',''),'hex')
      );

INSERT INTO measurement(id, ec, timestamp, device_id)
VALUES(decode(replace('172417a8-c340-4726-be62-48628e87dab7'::text,'-',''),'hex'),
       2.1,
       '2021-11-20 13:25:00',
       decode(replace('dc336d44-f0c2-413f-a6c2-3b3f99100b13'::text,'-',''),'hex')
      );