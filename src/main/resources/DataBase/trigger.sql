DELIMITER $$

CREATE TRIGGER update_user_grade AFTER INSERT ON Reservation
    FOR EACH ROW
BEGIN
    DECLARE total_payment INT;

    -- 해당 유저의 총 payment 계산
    SELECT SUM(payment) INTO total_payment
    FROM Reservation
    WHERE userid = NEW.userid;

    -- 등급 업데이트 로직
    IF total_payment > 400000 THEN
    UPDATE User
    SET gnum = 5
    WHERE userid = NEW.userid;
    ELSEIF total_payment > 300000 THEN
    UPDATE User
    SET gnum = 4
    WHERE userid = NEW.userid;
    ELSEIF total_payment > 200000 THEN
    UPDATE User
    SET gnum = 3
    WHERE userid = NEW.userid;
    ELSEIF total_payment > 100000 THEN
    UPDATE User
    SET gnum = 2
    WHERE userid = NEW.userid;
    ELSE
    UPDATE User
    SET gnum = 1
    WHERE userid = NEW.userid;
END IF;
END$$

DELIMITER ;