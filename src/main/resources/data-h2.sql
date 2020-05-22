INSERT INTO transaction
(transaction_id, action_type, deal_type, quantity, security_code, trade_id, version)
VALUES(nextval('seq_transaction_id'), 'INSERT', 'BUY', 20, 'REL', 1, 1);
INSERT INTO transaction
(transaction_id, action_type, deal_type, quantity, security_code, trade_id, version)
VALUES(nextval('seq_transaction_id'), 'INSERT', 'BUY', 10, 'REL', 2, 1);
INSERT INTO transaction
(transaction_id, action_type, deal_type, quantity, security_code, trade_id, version)
VALUES(nextval('seq_transaction_id'), 'INSERT', 'SELL', 80, 'REL', 3, 1);
INSERT INTO transaction
(transaction_id, action_type, deal_type, quantity, security_code, trade_id, version)
VALUES(nextval('seq_transaction_id'), 'UPDATE', 'SELL', 60, 'YNN', 4, 2);
INSERT INTO transaction
(transaction_id, action_type, deal_type, quantity, security_code, trade_id, version)
VALUES(nextval('seq_transaction_id'), 'INSERT', 'SELL', 80, 'YNN', 4, 1);
INSERT INTO transaction
(transaction_id, action_type, deal_type, quantity, security_code, trade_id, version)
VALUES(nextval('seq_transaction_id'), 'INSERT', 'BUY', 90, 'ZHZ', 5, 1);
INSERT INTO transaction
(transaction_id, action_type, deal_type, quantity, security_code, trade_id, version)
VALUES(nextval('seq_transaction_id'), 'CANCEL', 'BUY', 90, 'YNN', 5, 2);

INSERT INTO trade_id_sequence
(sequence_id)
VALUES(nextval('seq_trade_id_sequence_id'));
INSERT INTO trade_id_sequence
(sequence_id)
VALUES(nextval('seq_trade_id_sequence_id'));
INSERT INTO trade_id_sequence
(sequence_id)
VALUES(nextval('seq_trade_id_sequence_id'));
INSERT INTO trade_id_sequence
(sequence_id)
VALUES(nextval('seq_trade_id_sequence_id'));
INSERT INTO trade_id_sequence
(sequence_id)
VALUES(nextval('seq_trade_id_sequence_id'));
INSERT INTO trade_id_sequence
(sequence_id)
VALUES(nextval('seq_trade_id_sequence_id'));
INSERT INTO trade_id_sequence
(sequence_id)
VALUES(nextval('seq_trade_id_sequence_id'));