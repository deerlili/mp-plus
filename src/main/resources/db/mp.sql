SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mp_user
-- ----------------------------
DROP TABLE IF EXISTS `mp_user`;
CREATE TABLE `mp_user`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `manager_id` bigint(20) NULL DEFAULT NULL COMMENT '上级id',
  `role_id` int DEFAULT 0 COMMENT '权限id',
  `status` tinyint DEFAULT 0 COMMENT '状态',
  `version` tinyint DEFAULT 0 COMMENT '版本',
  `is_delete` tinyint DEFAULT 0 COMMENT '（0：未删除，1：删除）',
  `create_time` datetime(0) DEFAULT NOW() COMMENT '创建时间',
  `update_time` datetime(0) DEFAULT NOW() COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

alter table mp_user change column id id bigint(20) auto_increment;

-- 乐观锁
update mp_user set version = newVersion,x = a,y = b
where version = oldVersion and z= c;


