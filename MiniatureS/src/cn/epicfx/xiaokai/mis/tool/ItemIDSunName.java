package cn.epicfx.xiaokai.mis.tool;

import java.util.HashMap;
import java.util.Map;

public enum ItemIDSunName {
	STONE("石头", 1, 0, "textures/blocks/stone.png"), STONE_GRANITE("花岗岩", 1, 1, "textures/blocks/stone_granite.png"),
	STONE_GRANITE_SMOOTH("磨制花岗岩", 1, 2, "textures/blocks/stone_granite_smooth.png"),
	STONE_DIORITE("闪长岩", 1, 3, "textures/blocks/stone_diorite.png"),
	STONE_DIORITE_SMOOTH("磨制闪长岩", 1, 4, "textures/blocks/stone_diorite_smooth.png"),
	STONE_ANDESITE("安山岩", 1, 5, "textures/blocks/stone_andesite.png"),
	STONE_ANDESITE_SMOOTH("磨制安山岩", 1, 6, "textures/blocks/stone_andesite_smooth.png"),
	GRASS("草方块", 2, 0, "textures/blocks/grass_side_carried.png"), DIRT("泥土", 3, 0, "textures/blocks/dirt.png"),
	COBBLESTONE("圆石", 4, 0, "textures/blocks/cobblestone.png"), PLANKS("橡树木板", 5, 0, "textures/blocks/planks_oak.png"),
	PLANKS_SPRUCE("云杉木板", 5, 1, "textures/blocks/planks_spruce.png"),
	PLANKS_BIRCH("桦木板", 5, 2, "textures/blocks/planks_birch.png"),
	PLANKS_JUNGLE("丛林树木板", 5, 3, "textures/blocks/planks_jungle.png"),
	PLANKS_ACACIA("金合欢木板", 5, 4, "textures/blocks/planks_acacia.png"),
	PLANKS_BIG_OAK("深色橡木木板", 5, 5, "textures/blocks/planks_big_oak.png"),
	SAPLING("橡树苗", 6, 0, "textures/blocks/sapling_oak.png"),
	SAPLING_SPRUCE("云杉树苗", 6, 1, "textures/blocks/sapling_spruce.png"),
	SAPLING_BIRCH("桦树苗", 6, 2, "textures/blocks/sapling_birch.png"),
	SAPLING_JUNGLE("丛林树苗", 6, 3, "textures/blocks/sapling_jungle.png"),
	SAPLING_ACACIA("金合欢树苗", 6, 4, "textures/blocks/sapling_acacia.png"),
	SAPLING_ROOFED_OAK("深色橡树苗", 6, 5, "textures/blocks/sapling_roofed_oak.png"),
	BEDROCK("基岩", 7, 0, "textures/blocks/bedrock.png"),
	FLOWING_WATER("流动的水", 8, 0, "textures/blocks/water_placeholder.png"),
	WATER("水", 9, 0, "textures/blocks/water_placeholder.png"),
	FLOWING_LAVA("流动的岩浆", 10, 0, "textures/blocks/lava_placeholder.png"),
	LAVA("岩浆", 11, 0, "textures/blocks/lava_placeholder.png"), SAND("沙子", 12, 0, "textures/blocks/sand.png"),
	RED_SAND("红沙", 12, 1, "textures/blocks/red_sand.png"), GRAVEL("砾石", 13, 0, "textures/blocks/gravel.png"),
	GOLD_ORE("金矿石", 14, 0, "textures/blocks/gold_ore.png"), IRON_ORE("铁矿石", 15, 0, "textures/blocks/iron_ore.png"),
	COAL_ORE("煤矿石", 16, 0, "textures/blocks/coal_ore.png"), LOG("橡木", 17, 0, "textures/blocks/log_oak.png"),
	LOG_SPRUCE("云杉木", 17, 1, "textures/blocks/log_spruce.png"), LOG_BIRCH("桦木", 17, 2, "textures/blocks/log_birch.png"),
	LOG_JUNGLE("丛林木", 17, 3, "textures/blocks/log_jungle.png"),
	LEAVES("橡树叶", 18, 0, "textures/blocks/leaves_oak_carried.tga"),
	LEAVES_SPRUCE_CARRIED("云杉叶", 18, 1, "textures/blocks/leaves_spruce_carried.tga"),
	LEAVES_BIRCH_CARRIED("桦树叶", 18, 2, "textures/blocks/leaves_birch_carried.tga"),
	LEAVES_JUNGLE_CARRIED("丛林树叶", 18, 3, "textures/blocks/leaves_jungle_carried.tga"),
	SPONGE("干海绵", 19, 0, "textures/blocks/sponge.png"), SPONGE_WET("湿海绵", 19, 1, "textures/blocks/sponge_wet.png"),
	GLASS("玻璃", 20, 0, "textures/blocks/glass.png"), LAPIS_ORE("青金石矿", 21, 0, "textures/blocks/lapis_ore.png"),
	LAPIS_BLOCK("青金石块", 22, 0, "textures/blocks/lapis_block.png"),
	DISPENSER("发射器", 23, 0, "textures/blocks/dispenser_front_horizontal.png"),
	SANDSTONE("沙石", 24, 0, "textures/blocks/sandstone_normal.png"),
	SANDSTONE_CARVED("錾制沙石", 24, 1, "textures/blocks/sandstone_carved.png"),
	SANDSTONE_SMOOTH("光滑沙石", 24, 2, "textures/blocks/sandstone_smooth.png"),
	NOTEBLOCK("音符盒", 25, 0, "textures/blocks/noteblock.png"),
	BED_BLOCK("方块床", 26, 0, "textures/blocks/bed_head_top.png"),
	GOLDEN_RAIL("动力铁轨", 27, 0, "textures/blocks/rail_golden.png"),
	DETECTOR_RAIL("探测铁轨", 28, 0, "textures/blocks/rail_detector.png"),
	STICKY_PISTON("粘性活塞", 29, 0, "textures/blocks/piston_top_sticky.png"), WEB("蜘蛛网", 30, 0, "textures/blocks/web.png"),
	TALLGRASS("高草", 31, 0, "textures/blocks/deadbush.png"),
	TALLGRASS_CARRIED("草", 31, 1, "textures/blocks/tallgrass_carried.tga"),
	FERN_CARRIED("蕨", 31, 2, "textures/blocks/fern_carried.tga"),
	DEADBUSH("枯死的灌木", 32, 0, "textures/blocks/deadbush.png"),
	PISTON("活塞", 33, 0, "textures/blocks/piston_top_normal.png"),
	PISTON_HEAD("活塞臂", 34, 0, "textures/blocks/piston_top_normal.png"),
	WOOL("白色羊毛", 35, 0, "textures/blocks/wool_colored_white.png"),
	WOOL_COLORED_ORANGE("橙色羊毛", 35, 1, "textures/blocks/wool_colored_orange.png"),
	WOOL_COLORED_MAGENTA("品红色羊毛", 35, 2, "textures/blocks/wool_colored_magenta.png"),
	WOOL_COLORED_LIGHT_BLUE("淡蓝色羊毛", 35, 3, "textures/blocks/wool_colored_light_blue.png"),
	WOOL_COLORED_YELLOW("黄色羊毛", 35, 4, "textures/blocks/wool_colored_yellow.png"),
	WOOL_COLORED_LIME("黄绿色羊毛", 35, 5, "textures/blocks/wool_colored_lime.png"),
	WOOL_COLORED_PINK("粉红色羊毛", 35, 6, "textures/blocks/wool_colored_pink.png"),
	WOOL_COLORED_GRAY("灰色羊毛", 35, 7, "textures/blocks/wool_colored_gray.png"),
	WOOL_COLORED_SILVER("淡灰色羊毛", 35, 8, "textures/blocks/wool_colored_silver.png"),
	WOOL_COLORED_CYAN("青色羊毛", 35, 9, "textures/blocks/wool_colored_cyan.png"),
	WOOL_COLORED_PURPLE("紫色羊毛", 35, 10, "textures/blocks/wool_colored_purple.png"),
	WOOL_COLORED_BLUE("蓝色羊毛", 35, 11, "textures/blocks/wool_colored_blue.png"),
	WOOL_COLORED_BROWN("棕色羊毛", 35, 12, "textures/blocks/wool_colored_brown.png"),
	WOOL_COLORED_GREEN("绿色羊毛", 35, 13, "textures/blocks/wool_colored_green.png"),
	WOOL_COLORED_RED("红色羊毛", 35, 14, "textures/blocks/wool_colored_red.png"),
	WOOL_COLORED_BLACK("黑色羊毛", 35, 15, "textures/blocks/wool_colored_black.png"),
	YELLOW_FLOWER("黄花羊毛", 37, 0, "textures/blocks/Dandelion.png"),
	RED_FLOWER("罂粟", 38, 0, "textures/blocks/flower_rose.png"),
	FLOWER_BLUE_ORCHID("蓝色的兰花", 38, 1, "textures/blocks/flower_blue_orchid.png"),
	FLOWER_ALLIUM("绒球葱", 38, 2, "textures/blocks/flower_allium.png"),
	FLOWER_HOUSTONIA("茜草花", 38, 3, "textures/blocks/flower_houstonia.png"),
	FLOWER_TULIP_RED("红色郁金香", 38, 4, "textures/blocks/flower_tulip_red.png"),
	FLOWER_TULIP_ORANGE("橙色郁金香", 38, 5, "textures/blocks/flower_tulip_orange.png"),
	FLOWER_TULIP_WHITE("白色郁金香", 38, 6, "textures/blocks/flower_tulip_white.png"),
	FLOWER_TULIP_PINK("粉色郁金香", 38, 7, "textures/blocks/flower_tulip_pink.png"),
	FLOWER_OXEYE_DAISY("滨菊", 38, 8, "textures/blocks/flower_oxeye_daisy.png"),
	BROWN_MUSHROOM("棕色蘑菇", 39, 0, "textures/blocks/mushroom_brown.png"),
	RED_MUSHROOM("红色蘑菇", 40, 0, "textures/blocks/mushroom_red.png"),
	GOLD_BLOCK("黄金块", 41, 0, "textures/blocks/gold_block.png"),
	IRON_BLOCK("铁块", 42, 0, "textures/blocks/iron_block.png"),
	DOUBLE_STONE_SLAB("双石台阶", 43, 0, "textures/blocks/stone_slab_side.png"),
	SANDSTONE_BOTTOM("双沙石台阶", 43, 1, "textures/blocks/sandstone_bottom.png"),
	PLANKS_OAK("双橡木台阶", 43, 2, "textures/blocks/planks_oak.png"),
	DOUBLE_PEBBLE_STEPS("双圆石台阶", 43, 3, "textures/blocks/cobblestone.png"),
	DOUBLE_BRICK_STEPS("双砖台阶", 43, 4, "textures/blocks/brick.png"),
	DOUBLE_STONE_BRICK_STEPS("双石砖台阶", 43, 5, "textures/blocks/stonebrick.png"),
	DOUBLE_QUARTZ_STEPS("双石英台阶", 43, 6, "textures/blocks/nether_brick.png"),
	DOUBLE_HELL_BRICK_STEPS("双地狱砖台阶", 43, 7, "textures/blocks/quartz_block_top.png"),
	STONE_SLAB("石台阶", 44, 0, "textures/blocks/stone_slab_top.png"),
	SANDSTONE_TOP("沙石台阶", 44, 1, "textures/blocks/sandstone_top.png"),
	COBBLESTONE_STEPS("圆石台阶", 44, 3, "textures/blocks/cobblestone.png"),
	BRICK_STEPS("砖台阶", 44, 4, "textures/blocks/brick.png"),
	STONEBRICK_STEPS("石砖台阶", 44, 5, "textures/blocks/stonebrick.png"),
	NETHER_BRICK_STEPS("石英台阶", 44, 6, "textures/blocks/nether_brick.png"),
	QUARTZ_BLOCK_TOP_STEPS("地狱砖台阶", 44, 7, "textures/blocks/quartz_block_top.png"),
	BRICK("砖", 336, 0, "textures/blocks/brick.png"), TNT("TNT", 46, 0, "textures/blocks/tnt_side.png"),
	BOOKSHELF("书架", 47, 0, "textures/blocks/bookshelf.png"),
	MOSSY_COBBLESTONE("苔石", 48, 0, "textures/blocks/cobblestone_mossy.png"),
	OBSIDIAN("黑曜石", 49, 0, "textures/blocks/obsidian.png"), TORCH("火把", 50, 0, "textures/blocks/torch_on.png"),
	FIRE("火", 51, 0, "textures/blocks/fire_0_placeholder.png"),
	MOB_SPAWNER("刷怪笼", 52, 0, "textures/blocks/mob_spawner.png"),
	OAK_STAIRS("橡木阶梯", 53, 0, "textures/blocks/planks_oak.png"), CHEST("箱子", 54, 0, "textures/blocks/chest_front.png"),
	REDSTONE_WIRE("红石粉", 55, 0, "textures/blocks/redstone_dust_line.png"),
	DIAMOND_ORE("钻石矿", 56, 0, "textures/blocks/diamond_ore.png"),
	DIAMOND_BLOCK("钻石块", 57, 0, "textures/blocks/diamond_block.png"),
	CRAFTING_TABLE("工作台", 58, 0, "textures/blocks/crafting_table_top.png"),
	FARMLAND("农田", 60, 0, "textures/blocks/farmland_dry.png"),
	FURNACE("熔炉", 61, 0, "textures/blocks/furnace_front_off.png"), LADDER("梯子", 65, 0, "textures/blocks/ladder.png"),
	RAIL("铁轨", 66, 0, "textures/blocks/rail_normal.png"),
	STONE_STAIRS("圆石阶梯", 67, 0, "textures/blocks/cobblestone.png"), LEVER("拉杆", 69, 0, "textures/blocks/lever.png"),
	STONE_PRESSURE_PLATE("石质压力板", 70, 0, "textures/blocks/stone.png"),
	WOODEN_PRESSURE_PLATE("木质压力板", 72, 0, "textures/blocks/planks_oak.png"),
	REDSTONE_ORE("红石矿", 73, 0, "textures/blocks/redstone_ore.png"),
	LIT_REDSTONE_ORE("发光的红石矿", 74, 0, "textures/blocks/redstone_ore.png"),
	UNLIT_REDSTONE_TORCH("红石火把", 75, 0, "textures/blocks/redstone_torch_off.png"),
	STONE_BUTTON("石质按钮", 77, 0, "textures/blocks/stone.png"), SNOW_LAYER("顶层雪", 78, 0, "textures/blocks/snow.png"),
	ICE("冰", 79, 0, "textures/blocks/ice.png"), SNOW("雪", 80, 0, "textures/blocks/snow.png"),
	CACTUS("仙人掌", 81, 0, "textures/blocks/cactus_side.tga"), CLAY("粘土", 82, 0, "textures/blocks/clay.png"),
	JUKEBOX("音乐盒", 84, 0, "textures/blocks/jukebox_top.png"), FENCE("橡木围墙", 85, 0, "textures/blocks/planks_oak.png"),
	PUMPKIN("南瓜", 86, 0, "textures/blocks/pumpkin_face_off.png"),
	NETHERRACK("地狱岩", 87, 0, "textures/blocks/netherrack.png"),
	SOUL_SAND("灵魂沙", 88, 0, "textures/blocks/soul_sand.png"), GLOWSTONE("萤石", 89, 0, "textures/blocks/glowstone.png"),
	PORTAL("传送门", 90, 0, "textures/blocks/portal_placeholder.png"),
	LIT_PUMPKIN("南瓜灯", 91, 0, "textures/blocks/pumpkin_face_on.png"),
	STAINED_GLASS("隐形基岩", 95, 0, "textures/blocks/glass_white.png"),
	TRAPDOOR("木质陷阱门", 96, 0, "textures/blocks/trapdoor.png"), MONSTER_EGG("石头刷怪蛋", 97, 0, "textures/blocks/stone.png"),
	COBBLESTONE_EGG("圆石刷怪蛋", 97, 1, "textures/blocks/Cobblestone.png"),
	STONEBRICK("石砖刷怪蛋", 97, 2, "textures/blocks/stonebrick.png"),
	STONEBRICK_MOSSY("苔石砖", 98, 1, "textures/blocks/stonebrick_mossy.png"),
	STONEBRICK_CRACKED("裂石砖", 98, 2, "textures/blocks/stonebrick_cracked.png"),
	STONEBRICK_CARVED("錾制石砖", 98, 3, "textures/blocks/stonebrick_carved.png"),
	BROWN_MUSHROOM_BLOCK("棕色蘑菇块", 99, 0, "textures/blocks/mushroom_block_skin_brown.png"),
	RED_MUSHROOM_BLOCK("红色蘑菇块", 100, 0, "textures/blocks/mushroom_block_skin_red.png"),
	IRON_BARS("铁栏杆", 101, 0, "textures/blocks/iron_bars.png"),
	GLASS_PANE("玻璃板", 102, 0, "textures/blocks/glass_pane_top.png"),
	PUMPKIN_STEM("南瓜梗", 104, 0, "textures/blocks/pumpkin_stem_disconnected.png"),
	VINE("藤蔓", 106, 0, "textures/blocks/vine_carried.png"),
	FENCE_GATE("橡木围墙大门", 107, 0, "textures/blocks/planks_oak.png"),
	BRICK_STAIRS("砖块阶梯", 108, 0, "textures/blocks/brick.png"),
	STONE_BRICK_STAIRS("石砖阶梯", 109, 0, "textures/blocks/stonebrick.png"),
	MYCELIUM("菌丝", 110, 0, "textures/blocks/mycelium_side.png"),
	WATERLILY("睡莲", 111, 0, "textures/blocks/carried_waterlily.png"),
	NETHERBRICK("地狱砖", 405, "textures/blocks/0nether_brick.png"),
	NETHER_BRICK_FENCE("地狱砖围墙", 113, 0, "textures/blocks/nether_brick.png"),
	NETHER_BRICK_STAIRS("地狱砖阶梯", 114, 0, "textures/blocks/nether_brick.png"),
	ENCHANTING_TABLE("附魔台", 116, 0, "textures/blocks/enchanting_table_side.png"),
	BREWING_STAND("酿造台", 379, 0, "textures/blocks/brewing_stand.png"),
	CAULDRON("炼药锅", 380, 0, "textures/blocks/cauldron_side.png"),
	END_PORTAL("末地门", 119, 0, "textures/blocks/end_portal.png"),
	END_PORTAL_FRAME("末地传送门", 120, 0, "textures/blocks/end_portal.png"),
	END_STONE("末地石", 121, 0, "textures/blocks/end_stone.png"),
	DRAGON_EGG("龙蛋", 122, 0, "textures/blocks/dragon_egg.png"),
	REDSTONE_LAMP("红石灯", 123, 0, "textures/blocks/redstone_lamp_off.png"),
	SANDSTONE_STAIRS("沙石阶梯", 128, 0, "textures/blocks/sandstone_bottom.png"),
	EMERALD_ORE("绿宝石矿石", 129, 0, "textures/blocks/emerald_ore.png"),
	ENDER_CHEST("末影箱", 130, 0, "textures/blocks/ender_chest_front.png"),
	TRIPWIRE_HOOK("拌线钩", 131, 0, "textures/blocks/trip_wire_source.png"),
	TRIPWIRE("拌线", 132, 0, "textures/blocks/trip_wire.png"),
	EMERALD_BLOCK("绿宝石块", 133, 0, "textures/blocks/emerald_block.png"),
	SPRUCE_STAIRS("云杉木阶梯", 134, 0, "textures/blocks/planks_spruce.png"),
	BIRCH_STAIRS("桦木阶梯", 135, 0, "textures/blocks/planks_birch.png"),
	JUNGLE_STAIRS("丛林木阶梯", 136, 0, "textures/blocks/planks_jungle.png"),
	COMMAND_BLOCK("命令方块", 137, 0, "textures/blocks/command_block.png"),
	BEACON("信标", 138, 0, "textures/blocks/beacon.png"),
	COBBLESTONE_WALL("圆石墙", 139, 0, "textures/blocks/cobblestone.png"),
	MOSS_COBBLESTONE_WALL("苔石墙", 139, 1, "textures/blocks/cobblestone_mossy.png"),
	POTATOES("土豆", 142, 0, "textures/blocks/potatoes_stage_3.png"),
	WOODEN_BUTTON("木质按钮", 143, 0, "textures/blocks/planks_oak.png"),
	ANVIL("铁砧", 145, 0, "textures/blocks/anvil_top_damaged_0.png"),
	TRAPPED_CHEST("陷阱箱", 146, 0, "textures/blocks/trapped_chest_front.png"),
	LIGHT_WEIGHTED_PRESSURE_PLATE("重力压力板(轻型)", 147, 0, "textures/blocks/gold_block.png"),
	HEAVY_WEIGHTED_PRESSURE_PLATE("重力压力板(重型)", 148, 0, "textures/blocks/iron_block.png"),
	DAYLIGHT_DETECTOR_INVERTED("阳光传感器", 178, 0, "textures/blocks/daylight_detector_inverted_top.png"),
	REDSTONE_BLOCK("红石块", 152, 0, "textures/blocks/redstone_block.png"),
	QUARTZ_ORE("地狱石英矿石", 153, 0, "textures/blocks/quartz_ore.png"),
	HOPPER("漏斗", 154, 0, "textures/blocks/hopper_top.png"),
	QUARTZ_BLOCK("石英块", 155, 0, "textures/blocks/quartz_block_top.png"),
	VERTICAL_GRAIN_QUARTZ_BLOCK("竖纹石英块", 155, 1, "textures/blocks/quartz_block_lines.png"),
	QUARTZ_BLOCK_CHISELED("錾制石英块", 155, 2, "textures/blocks/quartz_block_chiseled_top.png"),
	QUARTZ_STAIRS("石英阶梯", 156, 0, "textures/blocks/quartz_block_top.png"),
	OAK_WOOD_STAIRS("橡木台阶", 158, 0, "textures/blocks/planks_oak.png"),
	WHITE_STAINED_HARDENED_CLAY("白色粘土", 159, 0, "textures/blocks/hardened_clay_stained_white.png"),
	ORANGE_STAINED_HARDENED_CLAY("橙色粘土", 159, 1, "textures/blocks/hardened_clay_stained_orange.png"),
	SOLFERINO_STAINED_HARDENED_CLAY("品红色粘土", 159, 2, "textures/blocks/hardened_clay_stained_magenta.png"),
	LIGHT_BLUE_STAINED_HARDENED_CLAY("淡蓝色粘土", 159, 3, "textures/blocks/hardened_clay_stained_light_blue.png"),
	YELLOW_STAINED_HARDENED_CLAY("黄色粘土", 159, 4, "textures/blocks/hardened_clay_stained_yellow.png"),
	OLIVINE_STAINED_HARDENED_CLAY("黄绿色粘土", 159, 5, "textures/blocks/hardened_clay_stained_lime.png"),
	PINK_STAINED_HARDENED_CLAY("粉红色粘土", 159, 6, "textures/blocks/hardened_clay_stained_pink.png"),
	GRAY_STAINED_HARDENED_CLAY("灰色粘土", 159, 7, "textures/blocks/hardened_clay_stained_gray.png"),
	LIGHT_GRAY_STAINED_HARDENED_CLAY("淡灰色粘土", 159, 8, "textures/blocks/hardened_clay_stained_light_gray.png"),
	CYAN_STAINED_HARDENED_CLAY("青色粘土", 159, 9, "textures/blocks/hardened_clay_stained_lime.png"),
	VIOLET_STAINED_HARDENED_CLAY("紫色粘土", 159, 10, "textures/blocks/hardened_clay_stained_purple.png"),
	BLUE_STAINED_HARDENED_CLAY("蓝色粘土", 159, 11, "textures/blocks/hardened_clay_stained_blue.png"),
	BROWN_STAINED_HARDENED_CLAY("棕色粘土", 159, 12, "textures/blocks/hardened_clay_stained_brown.png"),
	GREEN_STAINED_HARDENED_CLAY("绿色粘土", 159, 13, "textures/blocks/hardened_clay_stained_green.png"),
	RED_STAINED_HARDENED_CLAY("红色粘土", 159, 14, "textures/blocks/hardened_clay_stained_red.png"),
	BLACK_STAINED_HARDENED_CLAY("黑色粘土", 159, 15, "textures/blocks/hardened_clay_stained_black.png"),
	WHITE_STAINED_GLASS_PANE("白色玻璃板", 160, 0, "textures/blocks/XXXX.png"),
	ORANGE_STAINED_GLASS_PANE("橙色玻璃板", 160, 1, "textures/blocks/XXXX.png"),
	SOLFERINO_STAINED_GLASS_PANE("品红色玻璃板", 160, 2, "textures/blocks/XXXX.png"),
	LIGHT_BLUE_STAINED_GLASS_PANE("淡蓝色玻璃板", 160, 3, "textures/blocks/XXXX.png"),
	YELLOW_STAINED_GLASS_PANE("黄色玻璃板", 160, 4, "textures/blocks/XXXX.png"),
	OLIVINE_STAINED_GLASS_PANE("黄绿色玻璃板", 160, 5, "textures/blocks/XXXX.png"),
	PINK_STAINED_GLASS_PANE("粉红色玻璃板", 160, 6, "textures/blocks/XXXX.png"),
	GRAY_STAINED_GLASS_PANE("灰色玻璃板", 160, 7, "textures/blocks/XXXX.png"),
	LIGHT_GRAY_STAINED_GLASS_PANE("淡灰色玻璃板", 160, 8, "textures/blocks/XXXX.png"),
	CYAN_STAINED_GLASS_PANE("青色玻璃板", 160, 9, "textures/blocks/XXXX.png"),
	VIOLET_STAINED_GLASS_PANE("紫色玻璃板", 160, 10, "textures/blocks/XXXX.png"),
	BLUE_STAINED_GLASS_PANE("蓝色玻璃板", 160, 11, "textures/blocks/XXXX.png"),
	BROWN_STAINED_GLASS_PANE("棕色玻璃板", 160, 12, "textures/blocks/XXXX.png"),
	GREEN_STAINED_GLASS_PANE("绿色玻璃板", 160, 13, "textures/blocks/XXXX.png"),
	RED_STAINED_GLASS_PANE("红色玻璃板", 160, 14, "textures/blocks/XXXX.png"),
	BLACK_STAINED_GLASS_PANE("黑色玻璃板", 160, 15, "textures/blocks/XXXX.png"),
	ACACIA_LEAVES("金合欢叶", 161, 0, "textures/blocks/XXXX.png"),
	DARK_OAK_LEAF("深色橡树叶", 161, 1, "textures/blocks/XXXX.png"), ACACIA_WOOD("金合欢木", 162, 0, "textures/blocks/XXXX.png"),
	DARK_OAK("深色橡木", 162, 1, "textures/blocks/XXXX.png"), ACACIA_STAIRS("金合欢木阶梯", 163, 0, "textures/blocks/XXXX.png"),
	DARK_OAK_STAIRS("深色橡木阶梯", 164, 0, "textures/blocks/XXXX.png"), SLIME("粘液块", 165, 0, "textures/blocks/XXXX.png"),
	IRON_DOOR("铁门", 330, 0, "textures/blocks/XXXX.png"), PRISMARINE("海晶石", 168, 0, "textures/blocks/XXXX.png"),
	DARK_PRISMARINE("暗海晶石", 168, 1, "textures/blocks/XXXX.png"),
	PRISMARINE_STONE_BRICK("海晶石砖", 168, 2, "textures/blocks/XXXX.png"),
	SEA_LANTERN("海晶灯", 169, 0, "textures/blocks/XXXX.png"), HAY_BLOCK("干草捆", 170, 0, "textures/blocks/XXXX.png"),
	WHITE_CARPET("白色地毯", 171, 0, "textures/blocks/XXXX.png"), ORANGE_CARPET("橙色地毯", 171, 1, "textures/blocks/XXXX.png"),
	SOLFERINO_CARPET("品红色地毯", 171, 2, "textures/blocks/XXXX.png"),
	LIGHT_BLUE_CARPET("淡蓝色地毯", 171, 3, "textures/blocks/XXXX.png"),
	YELLOW_CARPET("黄色地毯", 171, 4, "textures/blocks/XXXX.png"),
	OLIVINE_CARPET("黄绿色地毯", 171, 5, "textures/blocks/XXXX.png"),
	PINK_CARPET("粉红色地毯", 171, 6, "textures/blocks/XXXX.png"), GRAY_CARPET("灰色地毯", 171, 7, "textures/blocks/XXXX.png"),
	LIGHT_GRAY_CARPET("淡灰色地毯", 171, 8, "textures/blocks/XXXX.png"),
	CYAN_CARPET("青色地毯", 171, 9, "textures/blocks/XXXX.png"), VIOLET_CARPET("紫色地毯", 171, 10, "textures/blocks/XXXX.png"),
	BLUE_CARPET("蓝色地毯", 171, 11, "textures/blocks/XXXX.png"), BROWN_CARPET("棕色地毯", 171, 12, "textures/blocks/XXXX.png"),
	GREEN_CARPET("绿色地毯", 171, 13, "textures/blocks/XXXX.png"), RED_CARPET("红色地毯", 171, 14, "textures/blocks/XXXX.png"),
	BLACK_CARPET("黑色地毯", 171, 15, "textures/blocks/XXXX.png"),
	HARDENED_CLAY("硬化粘土", 172, 0, "textures/blocks/XXXX.png"), COAL_BLOCK("煤炭块", 173, 0, "textures/blocks/XXXX.png"),
	PACKED_ICE("浮冰", 174, 0, "textures/blocks/XXXX.png"), SUNFLOWER("向日葵", 175, 0, "textures/blocks/XXXX.png"),
	LILAC("丁香", 175, 1, "textures/blocks/XXXX.png"), TALL_GRASS("高草丛", 175, 2, "textures/blocks/XXXX.png"),
	LARGE_FERN("大型蕨", 175, 3, "textures/blocks/XXXX.png"), ROSE_BUSH("玫瑰丛", 175, 4, "textures/blocks/XXXX.png"),
	PEONY("牡丹", 175, 5, "textures/blocks/XXXX.png"), STANDING_BANNER("旗帜", 176, 0, "textures/blocks/XXXX.png"),
	WALL_BANNER("悬挂的旗帜", 177, 0, "textures/blocks/XXXX.png"), RED_SANDSTONE("红沙石", 179, 0, "textures/blocks/XXXX.png"),
	CHISELED_RED_SANDSTONE("錾制红沙石", 179, 1, "textures/blocks/XXXX.png"),
	SMOOTH_RED_SANDSTONE("平滑红沙石", 179, 2, "textures/blocks/XXXX.png"),
	RED_SANDSTONE_STAIRS("红沙石阶梯", 180, 0, "textures/blocks/XXXX.png"),
	STONE_SLAB2("红沙石台阶", 182, 0, "textures/blocks/XXXX.png"),
	SPRUCE_FENCE_GATE("云杉围墙大门", 183, 0, "textures/blocks/XXXX.png"),
	BIRCH_FENCE_GATE("桦木围墙大门", 184, 0, "textures/blocks/XXXX.png"),
	JUNGLE_FENCE_GATE("丛林木围墙大门", 185, 0, "textures/blocks/XXXX.png"),
	DARK_OAK_FENCE_GATE("深色橡木围墙大门", 186, 0, "textures/blocks/XXXX.png"),
	ACACIA_FENCE_GATE("金合欢木围墙大门", 187, 0, "textures/blocks/XXXX.png"),
	SPRUCE_FENCE("重复命令块", 188, 0, "textures/blocks/XXXX.png"), BIRCH_FENCE("链命令块", 189, 0, "textures/blocks/XXXX.png"),
	BIRCH_DOOR("桦木门", 194, 0, "textures/blocks/XXXX.png"), END_ROD("绿茵小道", 198, 0, "textures/blocks/XXXX.png"),
	CHORUS_FLOWER("合唱花", 200, 0, "textures/blocks/XXXX.png"), PURPUR_BLOCK("紫珀方块", 201, 0, "textures/blocks/XXXX.png"),
	PURPUR_STAIRS("紫珀阶梯", 203, 0, "textures/blocks/XXXX.png"),
	PURPUR_SLAB("潜匿之贝箱子", 205, 0, "textures/blocks/XXXX.png"), END_BRICKS("末地石砖", 206, 0, "textures/blocks/XXXX.png"),
	GRASS_PATH("末地棒", 208, 0, "textures/blocks/XXXX.png"), END_GATEWAY("末地门2", 209, 0, "textures/blocks/XXXX.png"),
	IRON_SHOVEL("铁锹", 256, 0, "textures/items/XXXX.png"), IRON_PICKAXE("铁镐", 257, 0, "textures/items/XXXX.png"),
	IRON_AXE("铁斧", 258, 0, "textures/items/XXXX.png"), FLINT_AND_STEEL("打火石", 259, 0, "textures/items/XXXX.png"),
	APPLE("苹果", 260, 0, "textures/items/XXXX.png"), BOW("弓", 261, 0, "textures/items/XXXX.png"),
	ARROW("箭", 262, 0, "textures/items/XXXX.png"), COAL("煤炭", 263, 0, "textures/items/XXXX.png"),
	CHARCOAL("木炭", 263, 1, "textures/items/XXXX.png"), DIAMOND("钻石", 264, 0, "textures/items/XXXX.png"),
	IRON_INGOT("铁锭", 265, 0, "textures/items/XXXX.png"), GOLD_INGOT("金锭", 266, 0, "textures/items/XXXX.png"),
	IRON_SWORD("铁剑", 267, 0, "textures/items/XXXX.png"), WOODEN_SWORD("木剑", 268, 0, "textures/items/XXXX.png"),
	WOODEN_SHOVEL("木锹", 269, 0, "textures/items/XXXX.png"), WOODEN_PICKAXE("木镐", 270, 0, "textures/items/XXXX.png"),
	WOODEN_AXE("木斧", 271, 0, "textures/items/XXXX.png"), STONE_SWORD("石剑", 272, 0, "textures/items/XXXX.png"),
	STONE_SHOVEL("石锹", 273, 0, "textures/items/XXXX.png"), STONE_PICKAXE("石镐", 274, 0, "textures/items/XXXX.png"),
	STONE_AXE("石斧", 275, 0, "textures/items/XXXX.png"), DIAMOND_SWORD("钻石剑", 276, 0, "textures/items/XXXX.png"),
	DIAMOND_SHOVEL("钻石锹", 277, 0, "textures/items/XXXX.png"), DIAMOND_PICKAXE("钻石镐", 278, 0, "textures/items/XXXX.png"),
	DIAMOND_AXE("钻石斧", 279, 0, "textures/items/XXXX.png"), STICK("木棍", 280, 0, "textures/items/XXXX.png"),
	BOWL("碗", 281, 0, "textures/items/XXXX.png"), MUSHROOM_STEW("蘑菇煲", 282, 0, "textures/items/XXXX.png"),
	GOLDEN_SWORD("金剑", 283, 0, "textures/items/XXXX.png"), GOLDEN_SHOVEL("金锹", 284, 0, "textures/items/XXXX.png"),
	GOLDEN_PICKAXE("金镐", 285, 0, "textures/items/XXXX.png"), GOLDEN_AXE("金斧", 286, 0, "textures/items/XXXX.png"),
	STRING("蛛丝", 287, 0, "textures/items/XXXX.png"), FEATHER("羽毛", 288, 0, "textures/items/XXXX.png"),
	GUNPOWDER("火药", 289, 0, "textures/items/XXXX.png"), WOODEN_HOE("木锄", 290, 0, "textures/items/XXXX.png"),
	STONE_HOE("石锄", 291, 0, "textures/items/XXXX.png"), IRON_HOE("铁锄", 292, 0, "textures/items/XXXX.png"),
	DIAMOND_HOE("钻石锄", 293, 0, "textures/items/XXXX.png"), GOLDEN_HOE("金锄", 294, 0, "textures/items/XXXX.png"),
	WHEAT_SEEDS("种子", 295, 0, "textures/items/XXXX.png"), WHEAT("小麦", 296, 0, "textures/items/XXXX.png"),
	BREAD("面包", 297, 0, "textures/items/XXXX.png"), LEATHER_HELMET("皮革头盔", 298, 0, "textures/items/XXXX.png"),
	LEATHER_CHESTPLATE("皮革胸甲", 299, 0, "textures/items/XXXX.png"),
	LEATHER_LEGGINGS("皮革护腿", 300, 0, "textures/items/XXXX.png"),
	LEATHER_BOOTS("皮革靴子", 301, 0, "textures/items/XXXX.png"),
	CHAINMAIL_HELMET("锁链头盔", 302, 0, "textures/items/XXXX.png"),
	CHAINMAIL_CHESTPLATE("锁链胸甲", 303, 0, "textures/items/XXXX.png"),
	CHAINMAIL_LEGGINGS("锁链护腿", 304, 0, "textures/items/XXXX.png"),
	CHAINMAIL_BOOTS("锁链靴子", 305, 0, "textures/items/XXXX.png"), IRON_HELMET("铁头盔", 306, 0, "textures/items/XXXX.png"),
	IRON_CHESTPLATE("铁胸甲", 307, 0, "textures/items/XXXX.png"), IRON_LEGGINGS("铁护腿", 308, 0, "textures/items/XXXX.png"),
	IRON_BOOTS("铁靴子", 309, 0, "textures/items/XXXX.png"), DIAMOND_HELMET("钻石头盔", 310, 0, "textures/items/XXXX.png"),
	DIAMOND_CHESTPLATE("钻石胸甲", 311, 0, "textures/items/XXXX.png"),
	DIAMOND_LEGGINGS("钻石护腿", 312, 0, "textures/items/XXXX.png"),
	DIAMOND_BOOTS("钻石靴子", 313, 0, "textures/items/XXXX.png"), GOLDEN_HELMET("金头盔", 314, 0, "textures/items/XXXX.png"),
	GOLDEN_CHESTPLATE("金胸甲", 315, 0, "textures/items/XXXX.png"),
	GOLDEN_LEGGINGS("金护腿", 316, 0, "textures/items/XXXX.png"), GOLDEN_BOOTS("金靴子", 317, 0, "textures/items/XXXX.png"),
	FLINT("燧石", 318, 0, "textures/items/XXXX.png"), PORKCHOP("生猪排", 319, 0, "textures/items/XXXX.png"),
	COOKED_PORKCHOP("熟猪排", 320, 0, "textures/items/XXXX.png"), PAINTING("画", 321, 0, "textures/items/XXXX.png"),
	GOLDEN_APPLE("金苹果", 322, 0, "textures/items/XXXX.png"), SIGN("告示牌", 323, 0, "textures/items/XXXX.png"),
	WOODEN_DOOR("橡木门", 324, 0, "textures/items/XXXX.png"), BUCKET("桶", 325, 0, "textures/items/XXXX.png"),
	MINECART("矿车", 328, 0, "textures/items/XXXX.png"), SADDLE("鞍", 329, 0, "textures/items/XXXX.png"),
	REDSTONE("红石", 331, 0, "textures/items/XXXX.png"), SNOWBALL("雪球", 332, 0, "textures/items/XXXX.png"),
	BOAT("橡木船", 333, 0, "textures/items/XXXX.png"), LEATHER("皮革", 334, 0, "textures/items/XXXX.png"),
	CLAY_BALL("粘土球", 337, 0, "textures/items/XXXX.png"), REEDS("甘蔗", 338, 0, "textures/items/XXXX.png"),
	PAPER("纸", 339, 0, "textures/items/XXXX.png"), BOOK("书", 340, 0, "textures/items/XXXX.png"),
	SLIME_BALL("粘液球", 341, 0, "textures/items/XXXX.png"), CHEST_MINECART("箱子矿车", 342, 0, "textures/items/XXXX.png"),
	EGG("鸡蛋", 344, 0, "textures/items/XXXX.png"), COMPASS("指南针", 345, 0, "textures/items/XXXX.png"),
	FISHING_ROD("鱼竿", 346, 0, "textures/items/XXXX.png"), CLOCK("时钟", 347, 0, "textures/items/XXXX.png"),
	GLOWSTONE_DUST("荧石粉", 348, 0, "textures/items/XXXX.png"), FISH("鱼", 349, 0, "textures/items/XXXX.png"),
	COOKED_FISH("熟鱼", 350, 0, "textures/items/XXXX.png"), DYE("墨囊", 351, 0, "textures/items/XXXX.png"),
	SOLFERINO_DYE("品红色染料", 351, 1, "textures/items/XXXX.png"), GREEN_DYE("绿色染料", 351, 2, "textures/items/XXXX.png"),
	BROWN_DYE("可可豆", 351, 3, "textures/items/XXXX.png"), BLUE_DYE("蓝色染料", 351, 4, "textures/items/XXXX.png"),
	VIOLET_DYE("紫色染料", 351, 5, "textures/items/XXXX.png"), CYAN_DYE("青色染料", 351, 6, "textures/items/XXXX.png"),
	LIGHT_GRAY_DYE("淡灰色染料", 351, 7, "textures/items/XXXX.png"), GRAY_DYE("灰色染料", 351, 8, "textures/items/XXXX.png"),
	PINK_DYE("粉红色染料", 351, 9, "textures/items/XXXX.png"), OLIVINE_DYE("黄绿色染料", 351, 10, "textures/items/XXXX.png"),
	YELLOW_DYE("黄色染料", 351, 11, "textures/items/XXXX.png"), LIGHT_BLUE_DYE("淡蓝色染料", 351, 12, "textures/items/XXXX.png"),
	RED_DYE("红色染料", 351, 13, "textures/items/XXXX.png"), ORANGE_DYE("橙色染料", 351, 14, "textures/items/XXXX.png"),
	WHITE_DYE("骨粉", 351, 15, "textures/items/XXXX.png"), BONE("骨头", 352, 0, "textures/items/XXXX.png"),
	SUGAR("糖", 353, 0, "textures/items/XXXX.png"), CAKE("蛋糕", 354, 0, "textures/items/XXXX.png"),
	BED("床", 355, 0, "textures/items/XXXX.png"), REPEATER("中继器", 356, 0, "textures/items/XXXX.png"),
	COOKIE("曲奇", 357, 0, "textures/items/XXXX.png"), FILLED_MAP("地图(满)", 358, 0, "textures/items/XXXX.png"),
	SHEARS("剪刀", 359, 0, "textures/items/XXXX.png"), MELON("西瓜", 360, 0, "textures/items/XXXX.png"),
	MELON_SEEDS("南瓜种子", 362, 0, "textures/items/XXXX.png"), BEEF("生牛肉", 363, 0, "textures/items/XXXX.png"),
	COOKED_BEEF("熟牛肉", 364, 0, "textures/items/XXXX.png"), CHICKEN("生鸡肉", 365, 0, "textures/items/XXXX.png"),
	COOKED_CHICKEN("熟鸡肉", 366, 0, "textures/items/XXXX.png"), ROTTEN_FLESH("腐肉", 367, 0, "textures/items/XXXX.png"),
	ENDER_PEARL("末影珍珠", 368, 0, "textures/items/XXXX.png"), BLAZE_ROD("烈焰棒", 369, 0, "textures/items/XXXX.png"),
	GHAST_TEAR("恶魂泪", 370, 0, "textures/items/XXXX.png"), GOLD_NUGGET("金粒", 371, 0, "textures/items/XXXX.png"),
	NETHER_WART("地狱疣", 372, 0, "textures/items/XXXX.png"), POTION("水瓶", 373, 0, "textures/items/XXXX.png"),
	GLASS_BOTTLE("玻璃瓶", 374, 0, "textures/items/XXXX.png"), SPIDER_EYE("蜘蛛眼", 375, 0, "textures/items/XXXX.png"),
	FERMENTED_SPIDER_EYE("发酵蜘蛛眼", 376, 0, "textures/items/XXXX.png"),
	BLAZE_POWDER("烈焰粉", 377, 0, "textures/items/XXXX.png"), MAGMA_CREAM("岩浆膏", 378, 0, "textures/items/XXXX.png"),
	ENDER_EYE("末影之眼", 381, 0, "textures/items/XXXX.png"), SPECKLED_MELON("金西瓜", 382, 0, "textures/items/XXXX.png"),
	EXPERIENCE_BOTTLE("经验瓶", 384, 0, "textures/items/XXXX.png"), FIRE_CHARGE("火球", 385, 0, "textures/items/XXXX.png"),
	EMERALD("绿宝石", 388, 0, "textures/items/XXXX.png"), ITEM_FRAME("展示框", 389, 0, "textures/items/XXXX.png"),
	FLOWER_POT("花盆", 390, 0, "textures/items/XXXX.png"), CARROT("胡萝卜", 391, 0, "textures/items/XXXX.png"),
	POTATO("马铃薯", 392, 0, "textures/items/XXXX.png"), BAKED_POTATO("烤马铃薯", 393, 0, "textures/items/XXXX.png"),
	POISONOUS_POTATO("毒马铃薯", 394, 0, "textures/items/XXXX.png"), MAP("空地图", 395, 0, "textures/items/XXXX.png"),
	GOLDEN_CARROT("金胡萝卜", 396, 0, "textures/items/XXXX.png"), SKELETON_SKULL("骷髅头", 397, 0, "textures/items/XXXX.png"),
	LEIERDA_SKULL("凋零骷髅头", 397, 1, "textures/items/XXXX.png"), ZOMBIE_SKULL("僵尸头", 397, 2, "textures/items/XXXX.png"),
	STEVE_SKULL("史蒂夫头", 397, 3, "textures/items/XXXX.png"), CREEPER_SKULL("苦力怕头", 397, 4, "textures/items/XXXX.png"),
	DRAGON_SKULL("龙头", 397, 5, "textures/items/XXXX.png"), CARROT_ON_A_STICK("胡萝卜杆", 398, 0, "textures/items/XXXX.png"),
	NETHER_STAR("下届之星", 399, 0, "textures/items/XXXX.png"), PUMPKIN_PIE("南瓜派", 400, 0, "textures/items/XXXX.png"),
	ENCHANTED_BOOK("附魔书", 403, 0, "textures/items/XXXX.png"), COMPARATOR("比较器", 404, 0, "textures/items/XXXX.png"),
	QUARTZ("地狱石英", 406, 0, "textures/items/XXXX.png"), TNT_MINECART("tnt矿车", 407, 0, "textures/items/XXXX.png"),
	HOPPER_MINECART("漏斗矿车", 408, 0, "textures/items/XXXX.png"),
	PRISMARINE_SHARD("海晶碎片", 409, 0, "textures/items/XXXX.png"),
	PRISMARINE_CRYSTALS("海晶灯粉", 410, 0, "textures/items/XXXX.png"), RABBIT("生兔子肉", 411, 0, "textures/items/XXXX.png"),
	COOKED_RABBIT("熟兔子肉", 412, 0, "textures/items/XXXX.png"), RABBIT_STEW("兔子煲", 413, 0, "textures/items/XXXX.png"),
	RABBIT_FOOT("兔子脚", 414, 0, "textures/items/XXXX.png"), RABBIT_HIDE("兔子皮", 415, 0, "textures/items/XXXX.png"),
	ARMOR_STAND("皮革马鞍", 416, 0, "textures/items/XXXX.png"), IRON_HORSE_ARMOR("铁马鞍", 417, 0, "textures/items/XXXX.png"),
	GOLDEN_HORSE_ARMOR("金马鞍", 418, 0, "textures/items/XXXX.png"),
	DIAMOND_HORSE_ARMOR("钻石马鞍", 419, 0, "textures/items/XXXX.png"), LEAD("栓绳", 420, 0, "textures/items/XXXX.png"),
	NAME_TAG("命名牌", 421, 0, "textures/items/XXXX.png"),
	COMMAND_BLOCK_MINECART("命令方块矿车", 422, 0, "textures/items/XXXX.png"),
	MUTTON("生羊肉", 423, 0, "textures/items/XXXX.png"), COOKED_MUTTON("熟羊肉", 424, 0, "textures/items/XXXX.png"),
	SPRUCE_DOOR("云杉木门", 427, 0, "textures/items/XXXX.png"), BIRCH_WOOD_DOOR("桦树木门", 428, 0, "textures/items/XXXX.png"),
	JUNGLE_DOOR("丛林木门", 429, 0, "textures/items/XXXX.png"), ACACIA_DOOR("金合欢木门", 430, 0, "textures/items/XXXX.png"),
	DARK_OAK_DOOR("深色橡木门", 431, 0, "textures/items/XXXX.png"), CHORUS_FRUIT("共鸣果", 432, 0, "textures/items/XXXX.png"),
	POPPED_CHORUS_FRUIT("爆裂共鸣果", 433, 0, "textures/items/XXXX.png"),
	DRAGON_BREATH("龙息", 437, 0, "textures/items/XXXX.png"), SPLASH_POTION("喷溅的水瓶", 438, 0, "textures/items/XXXX.png"),
	LINGERING_POTION("遗留的水瓶", 441, 0, "textures/items/XXXX.png"), SPRUCE_BOAT("翅鞘", 444, 0, "textures/items/XXXX.png"),
	BIRCH_BOAT("潜匿之壳", 445, 0, "textures/items/XXXX.png");
	private int ID, Damage;
	private String Name, Path;
	private static final Map<String, Map<String, Object>> NAME_MAP = new HashMap<>();
	private static final Map<String, Map<String, Object>> ID_MAP = new HashMap<>();
	private static final Map<String, ItemIDSunName> ItemIDSunName_MAP = new HashMap<>();
	static {
		for (ItemIDSunName item : ItemIDSunName.values()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ID", item.ID);
			map.put("Damage", item.Damage);
			map.put("Path", item.Path);
			map.put("Name", item.Name);
			NAME_MAP.put(item.Name, map);
			ID_MAP.put(item.ID + ":" + item.Damage, map);
			ItemIDSunName_MAP.put(item.ID + ":" + item.Damage, item);
		}
	}

	/**
	 * @param id   物品ID
	 * @param Name 物品名称
	 */
	private ItemIDSunName(int id, String Name) {
		this(Name, id, 0);
	}

	/**
	 * @param ID 物品ID
	 */
	private ItemIDSunName(int ID) {
		this("Unknown", ID);
	}

	/**
	 * @param ID 物品ID
	 */
	private ItemIDSunName(int ID, int Damage) {
		this("Unknown", ID, Damage);
	}

	/**
	 * @param Name 物品名称
	 * @param id   物品ID
	 */
	private ItemIDSunName(String Name, int id) {
		this(Name, id, 0);
	}

	/**
	 * @param Name 物品名称
	 * @param id   物品ID
	 * @param Path 物品贴图路径
	 */
	private ItemIDSunName(String Name, int id, String Path) {
		this(Name, id, 0, Path);
	}

	/**
	 * @param Name   物品名稱
	 * @param id     物品ID
	 * @param Damage 物品特殊值
	 */
	private ItemIDSunName(String Name, int id, int Damage) {
		this(Name, id, Damage, null);
	}

	/**
	 * @param Name 物品名称
	 * @param ID   物品ID（ID：特殊值）
	 */
	private ItemIDSunName(String Name, String ID) {
		this(Name, ID, null);
	}

	/**
	 * @param Name 物品名称
	 * @param ID   物品ID（ID：特殊值）
	 * @param Path 物品贴图路径
	 */
	private ItemIDSunName(String Name, String ID, String Path) {
		if (ID.contains(":")) {
			String[] IDs = ID.split(":");
			this.ID = Integer.valueOf(IDs[0]);
			if (IDs.length >= 2)
				this.Damage = Integer.valueOf(IDs[1]);
			else
				this.Damage = 0;
		} else {
			this.ID = Integer.valueOf(ID);
			this.Damage = 0;
		}
	}

	/**
	 * @param Name   物品名称
	 * @param id     物品ID
	 * @param Path   物品贴图路径
	 * @param Damage 物品特殊值
	 */
	private ItemIDSunName(String Name, int id, String Path, int Damage) {
		this(Name, id, Damage, Path);
	}

	/**
	 * @param Name   物品名称
	 * @param id     物品ID
	 * @param Damage 物品特殊值
	 * @param Path   物品贴图路径
	 */
	private ItemIDSunName(String Name, int id, int Damage, String Path) {
		this.ID = id;
		this.Name = Name;
		this.Damage = Damage;
		this.Path = Path;
	}

	/**
	 * @return 物品贴图路径
	 */
	public String getPath() {
		return this.Path;
	}

	/**
	 * @return 物品名称
	 */
	public String getName() {
		return this.Name;
	}

	/**
	 * @return 物品特殊值
	 */
	public int getDamage() {
		return this.Damage;
	}

	/**
	 * @return 物品ID
	 */
	public int getID() {
		return this.ID;
	}

	/**
	 * @param ID 物品ID
	 * @return 根据物品ID获取物品贴图路径
	 */
	public static String getIDByPath(int ID) {
		return getIDByPath(ID + ":0");
	}

	/**
	 * @param ID     物品ID
	 * @param Damage 物品特殊值
	 * @return 根据物品ID获取物品贴图路径
	 */
	public static String getIDByPath(int ID, int Damage) {
		return getIDByPath(ID + ":" + Damage);
	}

	/**
	 * @param ID 物品ID（ID：特殊值）
	 * @return 根据物品ID获取物品贴图路径（ID：特殊值）
	 */
	public static String getIDByPath(String ID) {
		Map<String, Object> map = ID_MAP.getOrDefault(ID, null);
		if (map == null || map.getOrDefault("Path", null) == null)
			return null;
		return String.valueOf(map.get("Path"));
	}

	/**
	 * @param ID 物品ID
	 * @return 根据物品ID获取物品名称
	 */
	public static String getIDByName(int ID) {
		return getIDByName(ID + ":0");
	}

	/**
	 * @param ID     物品ID
	 * @param Damage 物品特殊值
	 * @return 根据物品ID获取物品名称
	 */
	public static String getIDByName(int ID, int Damage) {
		return getIDByName(ID + ":" + Damage);
	}

	/**
	 * @param ID 物品ID（ID：特殊值）
	 * @return 根据物品ID获取物品名称
	 */
	public static String getIDByName(String ID) {
		Map<String, Object> map = ID_MAP.getOrDefault(ID, null);
		if (map == null || map.getOrDefault("Name", null) == null)
			return null;
		return String.valueOf(map.get("Name"));
	}

	/**
	 * @param Name 物品名称
	 * @return 根据物品名称获取物品ID
	 */
	public static int getNameByID(String Name) {
		Map<String, Object> map = NAME_MAP.getOrDefault(Name, null);
		if (map == null || map.getOrDefault("ID", null) == null)
			return 0;
		return Integer.valueOf(String.valueOf(map.get("ID")));
	}

	/**
	 * @param Name 物品名称
	 * @return 根据物品名称获取物品特殊值
	 */
	public static int getNameByDamage(String Name) {
		Map<String, Object> map = NAME_MAP.getOrDefault(Name, null);
		if (map == null || map.getOrDefault("Damage", null) == null)
			return 0;
		return Integer.valueOf(String.valueOf(map.get("Damage")));
	}

	/**
	 * @param Name 物品名称
	 * @return 根据物品名称获取物品贴图路径
	 */
	public static String getNameByPath(String Name) {
		Map<String, Object> map = NAME_MAP.getOrDefault(Name, null);
		if (map == null || map.getOrDefault("Path", null) == null)
			return null;
		return String.valueOf(map.get("Path"));
	}

	/**
	 * @param ID 物品ID
	 * @return 根据物品ID获取物品枚举对象
	 */
	public static ItemIDSunName getItem(int ID) {
		return getItem(ID, 0);
	}

	/**
	 * @param ID     物品ID
	 * @param Damage 物品特殊值
	 * @return 根据物品ID获取物品枚举对象
	 */
	public static ItemIDSunName getItem(int ID, int Damage) {
		return ItemIDSunName_MAP.getOrDefault(ID + ":" + Damage, null);
	}

	/**
	 * @param ID 物品名称
	 * @return 根据物品ID获取物品枚举对象
	 */
	public static ItemIDSunName getItem(String Name) {
		return getItem(getNameByID(Name), getNameByDamage(Name));
	}

	/**
	 * @param 物品ID（ID：特殊值）/物品名称
	 * @return 尝试解析冰获取物品ID（ID：特殊值）
	 */
	public static String UnknownToID(String ID) {
		if (!ID.contains(":")) {
			if (getNameByPath(ID) != null)
				return getNameByID(ID) + ":" + getNameByDamage(ID);
			else if (getIDByPath(ID + ":0") != null)
				return ID + ":0";
			else
				return null;
		} else {
			if (getIDByPath(ID) != null)
				return ID;
			else if (getNameByPath(ID) != null)
				return getNameByID(ID) + ":" + getNameByDamage(ID);
			else
				return null;
		}
	}

	/**
	 * @param string 物品ID/物品名称/物品贴图路径
	 * @return 尝试解析并获取物品贴图路径
	 */
	public static String UnknownToPath(String string) {
		if (UnknownToID(string) != null)
			return getIDByPath(UnknownToID(string));
		else
			return string;
	}
}