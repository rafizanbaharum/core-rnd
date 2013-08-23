-- http://explainextended.com/2009/03/17/hierarchical-queries-in-mysql/

CREATE DEFINER=`corernd`@`localhost` FUNCTION `corernd`.`hierarchy_connect_by_parent_eq_prior_id`(value INT) RETURNS int(11)
    READS SQL DATA
BEGIN
        DECLARE _id INT;
        DECLARE _parent INT;
        DECLARE _next INT;
        DECLARE CONTINUE HANDLER FOR NOT FOUND SET @id = NULL;

        SET _parent = @id;
        SET _id = -1;

        IF @id IS NULL THEN
                RETURN NULL;
        END IF;

        LOOP
                SELECT  MIN(g.id)
                INTO    @id
                FROM    rnd_group g, rnd_principal p, rnd_group_member m
                WHERE
                     p.id = g.id
                     and m.principal_id = p.id
                     and m.group_id = _parent
                        AND id > _id;
                IF @id IS NOT NULL OR _parent = @start_with THEN
                        SET @level = @level + 1;
                        RETURN @id;
                END IF;
                SET @level := @level - 1;
                SELECT  g.id, m.group_id
                INTO    _id, _parent
                FROM     rnd_group g, rnd_principal p, rnd_group_member m
                WHERE   g.id = _parent;
        END LOOP;       
END