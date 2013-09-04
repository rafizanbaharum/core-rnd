-- http://explainextended.com/2009/03/17/hierarchical-queries-in-mysql/

CREATE FUNCTION hierarchy_connect_by_parent_eq_prior_id(val INT) RETURNS INT
NOT DETERMINISTIC

READS SQL DATA

BEGIN

DECLARE _id INT;
DECLARE _parent INT;
DECLARE _next INT;

DECLARE CONTINUE HANDLER FOR NOT FOUND SET @id = NULL;

SET _parent = @id;
SET _id = 10000;
SET _id := @startwith - 1;

IF @id IS NULL THEN
RETURN NULL;
END IF;

LOOP

if _parent is not null then
  if @parent = '' then
    set @parent = _parent;
  else
        set @parent := CONCAT_WS('/',@parent,_parent);
        end if;
end if;

SELECT  MIN(principal_id)
INTO    @id
FROM    rnd_group_member
WHERE   group_id = _parent
AND principal_id > _id;

IF @id IS NOT NULL OR _parent = @start_with THEN

SET @level = @level + 1;
RETURN @id;
END IF;

SET @level := @level - 1;

if @level < -5 then
 return null;
 end if;

sELECT  group_id, principal_id
INTO    _id, _parent
FROM    rnd_group_member
WHERE   group_id = _parent;

END LOOP;

END;




SELECT @parent_id AS _user_id,
(
   SELECT @parent_id:=group_id
   FROM rnd_group_member
   WHERE principal_id = _user_id
) AS parent
FROM (
    SELECT
       @parent_id :=  1
    ) vars,
    rnd_group_member u
) a where parent is not null;