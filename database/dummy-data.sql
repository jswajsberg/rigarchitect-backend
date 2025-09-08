-- =====================================================================
-- INSERT COMPONENTS WITH METADATA SUPPORT
-- =====================================================================
INSERT INTO components (name, brand, type, compatibility_tag, price, stock_quantity, socket, ram_type, wattage, form_factor, gpu_length_mm, cooler_height_mm, psu_form_factor, pci_slots_required, extra_compatibility, metadata)
VALUES 

-- === BUDGET TIER CPUs ===
('AMD Ryzen 5 5600', 'AMD', 'CPU', 'AM4', 130.00, 20, 'AM4', NULL, 65, NULL, NULL, NULL, NULL, NULL, 
'{"cores": 6, "threads": 12, "base_clock": "3.5GHz", "boost_clock": "4.4GHz", "l3_cache": "32MB", "chipset_compatible": ["B450", "B550", "X570"], "ddr4_support": true, "ddr5_support": false, "integrated_graphics": false}',
'{"performance_score": 21000, "power_tier": "low", "template_tier": "entry", "use_cases": ["gaming", "general"], "efficiency_rating": 8.2, "features": {"overclockable": true, "integrated_graphics": false}}'),

('Intel Core i5-12400F', 'Intel', 'CPU', 'LGA1700', 135.00, 18, 'LGA1700', NULL, 65, NULL, NULL, NULL, NULL, NULL,
'{"cores": 6, "threads": 12, "base_clock": "2.5GHz", "boost_clock": "4.4GHz", "l3_cache": "18MB", "chipset_compatible": ["Z690", "B660", "H610"], "ddr4_support": true, "ddr5_support": true, "integrated_graphics": false}',
'{"performance_score": 22000, "power_tier": "low", "template_tier": "entry", "use_cases": ["gaming", "general"], "efficiency_rating": 8.5, "features": {"overclockable": false, "integrated_graphics": false}}'),

-- === BUDGET AM5 CPUs ===
('AMD Ryzen 5 7500F', 'AMD', 'CPU', 'AM5', 180.00, 25, 'AM5', 'DDR5', 65, NULL, NULL, NULL, NULL, NULL,
'{"cores": 6, "threads": 12, "base_clock": "3.7GHz", "boost_clock": "5.0GHz", "l3_cache": "32MB", "integrated_graphics": false}',
'{"performance_score": 24000, "power_tier": "moderate", "template_tier": "entry", "use_cases": ["gaming", "budget_gaming"], "efficiency_rating": 8.0, "features": {"overclockable": true, "integrated_graphics": false}}'),

('AMD Ryzen 5 7600', 'AMD', 'CPU', 'AM5', 200.00, 20, 'AM5', 'DDR5', 65, NULL, NULL, NULL, NULL, NULL,
'{"cores": 6, "threads": 12, "base_clock": "3.8GHz", "boost_clock": "5.1GHz", "l3_cache": "32MB", "integrated_graphics": true}',
'{"performance_score": 25000, "power_tier": "moderate", "template_tier": "entry", "use_cases": ["gaming", "productivity"], "efficiency_rating": 8.2, "features": {"overclockable": true, "integrated_graphics": true}}'),

-- === BUDGET INTEL LGA1700 CPUs ===
('Intel Core i5-12600K', 'Intel', 'CPU', 'LGA1700', 220.00, 18, 'LGA1700', 'DDR4', 125, NULL, NULL, NULL, NULL, NULL,
'{"cores": 10, "threads": 16, "base_clock": "3.7GHz", "boost_clock": "4.9GHz", "l3_cache": "20MB", "integrated_graphics": true}',
'{"performance_score": 28000, "power_tier": "moderate", "template_tier": "mid", "use_cases": ["gaming", "productivity", "streaming"], "efficiency_rating": 8.5, "features": {"overclockable": true, "integrated_graphics": true}}'),

-- === MID-RANGE CPUs ===
('AMD Ryzen 5 7600X', 'AMD', 'CPU', 'AM5', 200.00, 15, 'AM5', NULL, 105, NULL, NULL, NULL, NULL, NULL, 
'{"cores": 6, "threads": 12, "base_clock": "4.7GHz", "boost_clock": "5.3GHz", "l3_cache": "32MB", "chipset_compatible": ["X670E", "X670", "B650E", "B650"], "ddr5_support": true, "ddr4_support": false, "integrated_graphics": true}',
'{"performance_score": 27000, "power_tier": "moderate", "template_tier": "mid", "use_cases": ["gaming", "productivity", "streaming"], "efficiency_rating": 7.8, "features": {"overclockable": true, "integrated_graphics": true}}'),

('Intel Core i5-13600K', 'Intel', 'CPU', 'LGA1700', 290.00, 12, 'LGA1700', NULL, 125, NULL, NULL, NULL, NULL, NULL, 
'{"cores": 14, "threads": 20, "base_clock": "3.5GHz", "boost_clock": "5.1GHz", "l3_cache": "24MB", "chipset_compatible": ["Z790", "Z690", "B760", "B660"], "ddr5_support": true, "ddr4_support": true, "integrated_graphics": true}',
'{"performance_score": 35000, "power_tier": "moderate", "template_tier": "mid", "use_cases": ["gaming", "productivity", "streaming"], "efficiency_rating": 8.0, "features": {"overclockable": true, "integrated_graphics": true}}'),

('Intel Core i7-12700K', 'Intel', 'CPU', 'LGA1700', 320.00, 10, 'LGA1700', NULL, 125, NULL, NULL, NULL, NULL, NULL,
'{"cores": 12, "threads": 20, "base_clock": "3.6GHz", "boost_clock": "5.0GHz", "l3_cache": "25MB", "chipset_compatible": ["Z790", "Z690", "B760", "B660"], "ddr5_support": true, "ddr4_support": true, "integrated_graphics": true}',
'{"performance_score": 38000, "power_tier": "moderate", "template_tier": "mid", "use_cases": ["gaming", "content_creation", "streaming"], "efficiency_rating": 7.5, "features": {"overclockable": true, "integrated_graphics": true}}'),

-- === HIGH-END CPUs ===
('AMD Ryzen 9 7900X', 'AMD', 'CPU', 'AM5', 450.00, 8, 'AM5', NULL, 170, NULL, NULL, NULL, NULL, NULL,
'{"cores": 12, "threads": 24, "base_clock": "4.7GHz", "boost_clock": "5.6GHz", "l3_cache": "64MB", "chipset_compatible": ["X670E", "X670", "B650E", "B650"], "ddr5_support": true, "ddr4_support": false, "integrated_graphics": true}',
'{"performance_score": 55000, "power_tier": "high", "template_tier": "high", "use_cases": ["content_creation", "workstation", "gaming"], "efficiency_rating": 7.0, "features": {"overclockable": true, "integrated_graphics": true}}'),

-- === BUDGET GPUS ===
('AMD RX 6600', 'AMD', 'GPU', 'PCIE4', 230.00, 25, NULL, NULL, 132, NULL, 190, NULL, NULL, 2,
'{"pcie_version": "4.0", "memory": "8GB GDDR6", "memory_bus": "128-bit", "memory_speed": "14 Gbps", "boost_clock": "2491 MHz", "game_clock": "2044 MHz", "min_psu_watts": 450}',
'{"performance_score": 6500, "power_tier": "low", "template_tier": "entry", "use_cases": ["gaming", "1080p"], "efficiency_rating": 8.5, "features": {"ray_tracing": true, "dlss": false, "rgb": false}}'),

-- === MID-RANGE GPUS ===
('NVIDIA RTX 4060', 'NVIDIA', 'GPU', 'PCIE4', 300.00, 20, NULL, NULL, 115, NULL, 240, NULL, NULL, 2,
'{"pcie_version": "4.0", "memory": "8GB GDDR6", "memory_bus": "128-bit", "memory_speed": "17 Gbps", "boost_clock": "2460 MHz", "base_clock": "1830 MHz", "min_psu_watts": 550}',
'{"performance_score": 7000, "power_tier": "low", "template_tier": "mid", "use_cases": ["gaming", "1080p", "streaming"], "efficiency_rating": 9.0, "features": {"ray_tracing": true, "dlss": true, "rgb": false}}'),

('NVIDIA RTX 4070', 'NVIDIA', 'GPU', 'PCIE4', 580.00, 15, NULL, NULL, 200, NULL, 240, NULL, NULL, 2,
'{"pcie_version": "4.0", "memory": "12GB GDDR6X", "memory_bus": "192-bit", "memory_speed": "21 Gbps", "boost_clock": "2475 MHz", "base_clock": "1920 MHz", "min_psu_watts": 650}',
'{"performance_score": 11000, "power_tier": "moderate", "template_tier": "mid", "use_cases": ["gaming", "1440p", "content_creation"], "efficiency_rating": 8.5, "features": {"ray_tracing": true, "dlss": true, "rgb": false}}'),

('NVIDIA RTX 4070 Ti', 'NVIDIA', 'GPU', 'PCIE4', 780.00, 10, NULL, NULL, 285, NULL, 285, NULL, NULL, 3,
'{"pcie_version": "4.0", "memory": "12GB GDDR6X", "memory_bus": "192-bit", "memory_speed": "21 Gbps", "boost_clock": "2610 MHz", "base_clock": "2310 MHz", "min_psu_watts": 700}',
'{"performance_score": 14000, "power_tier": "moderate", "template_tier": "high", "use_cases": ["gaming", "1440p", "4k_gaming", "content_creation"], "efficiency_rating": 8.0, "features": {"ray_tracing": true, "dlss": true, "rgb": false}}'),

-- === HIGH-END GPUS ===
('NVIDIA RTX 4080', 'NVIDIA', 'GPU', 'PCIE4', 1100.00, 5, NULL, NULL, 320, NULL, 310, NULL, NULL, 3,
'{"pcie_version": "4.0", "memory": "16GB GDDR6X", "memory_bus": "256-bit", "memory_speed": "22.4 Gbps", "boost_clock": "2505 MHz", "base_clock": "2205 MHz", "min_psu_watts": 750}',
'{"performance_score": 18000, "power_tier": "high", "template_tier": "enthusiast", "use_cases": ["4k_gaming", "content_creation", "vr"], "efficiency_rating": 7.5, "features": {"ray_tracing": true, "dlss": true, "rgb": true}}'),

('NVIDIA RTX 4090', 'NVIDIA', 'GPU', 'PCIE4', 1600.00, 3, NULL, NULL, 450, NULL, 336, NULL, NULL, 4,
'{"pcie_version": "4.0", "memory": "24GB GDDR6X", "memory_bus": "384-bit", "memory_speed": "21 Gbps", "boost_clock": "2520 MHz", "base_clock": "2230 MHz", "min_psu_watts": 850}',
'{"performance_score": 25000, "power_tier": "extreme", "template_tier": "enthusiast", "use_cases": ["gaming", "4k_gaming", "content_creation", "3d_rendering", "vr"], "efficiency_rating": 6.5, "features": {"ray_tracing": true, "dlss": true, "rgb": true}}'),

-- === BUDGET TIER RAM ===
('Kingston Fury 16GB DDR4', 'Kingston', 'RAM', 'DDR4', 65.00, 40, NULL, 'DDR4', 8, NULL, NULL, NULL, NULL, NULL, 
'{"speed": "2666MHz", "cas_latency": "CL16", "voltage": "1.2V", "kit_config": "2x8GB", "heat_spreader": true}',
'{"performance_score": 4000, "power_tier": "low", "template_tier": "entry", "use_cases": ["general", "budget_gaming"], "efficiency_rating": 8.5, "features": {"rgb": false, "low_profile": true, "overclockable": true}}'),

('G.SKILL Ripjaws V 16GB DDR4', 'G.SKILL', 'RAM', 'DDR4', 75.00, 35, NULL, 'DDR4', 8, NULL, NULL, NULL, NULL, NULL,
'{"speed": "3200MHz", "cas_latency": "CL16", "voltage": "1.35V", "kit_config": "2x8GB", "heat_spreader": true, "timings": "16-18-18-38"}',
'{"performance_score": 5500, "power_tier": "low", "template_tier": "entry", "use_cases": ["gaming", "productivity"], "efficiency_rating": 8.2, "features": {"rgb": false, "low_profile": true, "overclockable": true, "xmp_support": true}}'),

-- === MID-RANGE RAM ===
('Corsair Vengeance 16GB DDR5', 'Corsair', 'RAM', 'DDR5', 110.00, 30, NULL, 'DDR5', 10, NULL, NULL, NULL, NULL, NULL, 
'{"speed": "5600MHz", "cas_latency": "CL40", "voltage": "1.25V", "kit_config": "1x16GB", "heat_spreader": true}',
'{"performance_score": 7500, "power_tier": "low", "template_tier": "mid", "use_cases": ["gaming", "productivity", "streaming"], "efficiency_rating": 7.8, "features": {"rgb": false, "low_profile": false, "overclockable": true, "xmp_support": true}}'),

('G.SKILL Trident Z5 32GB DDR5', 'G.SKILL', 'RAM', 'DDR5', 160.00, 25, NULL, 'DDR5', 12, NULL, NULL, NULL, NULL, NULL,
'{"speed": "6000MHz", "cas_latency": "CL30", "voltage": "1.35V", "kit_config": "2x16GB", "heat_spreader": true, "timings": "30-38-38-96"}',
'{"performance_score": 9000, "power_tier": "moderate", "template_tier": "high", "use_cases": ["gaming", "content_creation", "streaming"], "efficiency_rating": 7.5, "features": {"rgb": true, "low_profile": false, "overclockable": true, "xmp_support": true}}'),

-- === HIGH-END RAM ===
('Corsair Dominator 32GB DDR5', 'Corsair', 'RAM', 'DDR5', 220.00, 15, NULL, 'DDR5', 12, NULL, NULL, NULL, NULL, NULL, 
'{"speed": "6000MHz", "cas_latency": "CL30", "voltage": "1.35V", "kit_config": "2x16GB", "heat_spreader": true, "timings": "30-36-36-76"}',
'{"performance_score": 9500, "power_tier": "moderate", "template_tier": "enthusiast", "use_cases": ["content_creation", "3d_rendering", "workstation"], "efficiency_rating": 7.2, "features": {"rgb": true, "low_profile": false, "overclockable": true, "xmp_support": true, "premium_build": true}}'),

-- === BUDGET STORAGE ===
('Kingston NV2 500GB', 'Kingston', 'SSD', 'NVME', 35.00, 40, NULL, NULL, 3, NULL, NULL, NULL, NULL, NULL,
'{"interface": "PCIe 4.0 x4", "form_factor": "M.2 2280", "read_speed": "3500MB/s", "write_speed": "2100MB/s", "controller": "Phison E21T", "nand_type": "TLC"}',
'{"template_tier": "entry", "use_cases": ["general", "budget"], "efficiency_rating": 7.5, "features": {"dram_cache": false, "heatsink": false}}'),

('WD Blue 1TB SATA SSD', 'Western Digital', 'SSD', 'SATA', 95.00, 25, NULL, NULL, 5, NULL, NULL, NULL, NULL, NULL, 
'{"interface": "SATA III", "form_factor": "2.5 inch", "read_speed": "560MB/s", "write_speed": "530MB/s", "controller": "SanDisk", "nand_type": "TLC"}',
'{"template_tier": "entry", "use_cases": ["general", "productivity"], "efficiency_rating": 7.0, "features": {"dram_cache": true, "heatsink": false}}'),

('Seagate Barracuda 1TB', 'Seagate', 'HDD', 'SATA', 45.00, 50, NULL, NULL, 6, NULL, NULL, NULL, NULL, NULL, 
'{"interface": "SATA III", "rpm": "7200", "cache": "64MB", "form_factor": "3.5 inch"}',
'{"template_tier": "entry", "use_cases": ["general", "storage"], "efficiency_rating": 6.5, "features": {"quiet": false, "enterprise": false}}'),

-- === NEW BUDGET STORAGE ===
('Crucial P3 500GB', 'Crucial', 'SSD', 'NVME', 45.00, 40, NULL, NULL, 3, NULL, NULL, NULL, NULL, NULL,
'{"interface": "PCIe 3.0 x4", "form_factor": "M.2 2280", "read_speed": "3500MB/s", "write_speed": "1900MB/s", "controller": "Phison E19T", "nand_type": "QLC"}',
'{"template_tier": "entry", "use_cases": ["general", "budget"], "efficiency_rating": 7.0, "features": {"dram_cache": false, "heatsink": false}}'),

('Team MP33 1TB', 'TeamGroup', 'SSD', 'NVME', 60.00, 35, NULL, NULL, 4, NULL, NULL, NULL, NULL, NULL,
'{"interface": "PCIe 3.0 x4", "form_factor": "M.2 2280", "read_speed": "1800MB/s", "write_speed": "1500MB/s", "controller": "Phison E13T", "nand_type": "TLC"}',
'{"template_tier": "entry", "use_cases": ["budget_gaming", "general"], "efficiency_rating": 6.5, "features": {"dram_cache": false, "heatsink": false}}'),

('Crucial P3 Plus 1TB', 'Crucial', 'SSD', 'NVME', 75.00, 30, NULL, NULL, 4, NULL, NULL, NULL, NULL, NULL,
'{"interface": "PCIe 4.0 x4", "form_factor": "M.2 2280", "read_speed": "4800MB/s", "write_speed": "4100MB/s", "controller": "Phison E19T", "nand_type": "QLC"}',
'{"template_tier": "entry", "use_cases": ["gaming", "productivity"], "efficiency_rating": 7.5, "features": {"dram_cache": false, "heatsink": false}}'),

('Kingston NV2 SATA 500GB', 'Kingston', 'SSD', 'SATA', 35.00, 45, NULL, NULL, 3, NULL, NULL, NULL, NULL, NULL,
'{"interface": "SATA III", "form_factor": "2.5 inch", "read_speed": "520MB/s", "write_speed": "500MB/s", "controller": "Phison", "nand_type": "TLC"}',
'{"template_tier": "entry", "use_cases": ["general", "budget"], "efficiency_rating": 6.8, "features": {"dram_cache": false, "heatsink": false}}'),

('PNY CS900 1TB SATA', 'PNY', 'SSD', 'SATA', 55.00, 35, NULL, NULL, 4, NULL, NULL, NULL, NULL, NULL,
'{"interface": "SATA III", "form_factor": "2.5 inch", "read_speed": "535MB/s", "write_speed": "515MB/s", "controller": "Phison", "nand_type": "TLC"}',
'{"template_tier": "entry", "use_cases": ["budget_gaming", "general"], "efficiency_rating": 6.5, "features": {"dram_cache": false, "heatsink": false}}'),

('Toshiba P300 1TB', 'Toshiba', 'HDD', 'SATA', 40.00, 50, NULL, NULL, 6, NULL, NULL, NULL, NULL, NULL,
'{"interface": "SATA III", "rpm": "7200", "cache": "64MB", "form_factor": "3.5 inch"}',
'{"template_tier": "entry", "use_cases": ["storage", "general"], "efficiency_rating": 6.0, "features": {"quiet": false, "enterprise": false}}'),

('Seagate Barracuda Compute 500GB', 'Seagate', 'HDD', 'SATA', 25.00, 60, NULL, NULL, 5, NULL, NULL, NULL, NULL, NULL,
'{"interface": "SATA III", "rpm": "7200", "cache": "32MB", "form_factor": "3.5 inch"}',
'{"template_tier": "entry", "use_cases": ["budget", "storage"], "efficiency_rating": 5.8, "features": {"quiet": false, "enterprise": false}}'),

-- === MID-RANGE STORAGE ===
('Samsung 970 EVO Plus 500GB', 'Samsung', 'SSD', 'NVME', 80.00, 30, NULL, NULL, 4, NULL, NULL, NULL, NULL, NULL, 
'{"interface": "PCIe 3.0 x4", "form_factor": "M.2 2280", "read_speed": "3500MB/s", "write_speed": "3200MB/s", "controller": "Samsung Phoenix", "nand_type": "TLC"}',
'{"template_tier": "mid", "use_cases": ["gaming", "productivity"], "efficiency_rating": 8.0, "features": {"dram_cache": true, "heatsink": false}}'),

('Samsung 980 PRO 1TB NVMe', 'Samsung', 'SSD', 'NVME', 120.00, 20, NULL, NULL, 5, NULL, NULL, NULL, NULL, NULL, 
'{"interface": "PCIe 4.0 x4", "form_factor": "M.2 2280", "read_speed": "7000MB/s", "write_speed": "5000MB/s", "controller": "Samsung Elpis", "nand_type": "TLC"}',
'{"template_tier": "high", "use_cases": ["gaming", "content_creation", "workstation"], "efficiency_rating": 8.5, "features": {"dram_cache": true, "heatsink": false}}'),

('Seagate Barracuda 2TB', 'Seagate', 'HDD', 'SATA', 60.00, 35, NULL, NULL, 6, NULL, NULL, NULL, NULL, NULL, 
'{"interface": "SATA III", "rpm": "7200", "cache": "256MB", "form_factor": "3.5 inch"}',
'{"template_tier": "mid", "use_cases": ["storage", "content_creation"], "efficiency_rating": 7.0, "features": {"quiet": false, "enterprise": false}}'),

-- === HIGH-END STORAGE ===
('Samsung 990 PRO 2TB', 'Samsung', 'SSD', 'NVME', 280.00, 15, NULL, NULL, 6, NULL, NULL, NULL, NULL, NULL,
'{"interface": "PCIe 4.0 x4", "form_factor": "M.2 2280", "read_speed": "7450MB/s", "write_speed": "6900MB/s", "controller": "Samsung Pascal", "nand_type": "TLC"}',
'{"template_tier": "enthusiast", "use_cases": ["content_creation", "workstation", "3d_rendering"], "efficiency_rating": 9.0, "features": {"dram_cache": true, "heatsink": true}}'),

('WD Black SN850X 1TB', 'Western Digital', 'SSD', 'NVME', 120.00, 18, NULL, NULL, 5, NULL, NULL, NULL, NULL, NULL,
'{"interface": "PCIe 4.0 x4", "form_factor": "M.2 2280", "read_speed": "7300MB/s", "write_speed": "6300MB/s", "controller": "WD", "nand_type": "TLC"}',
'{"template_tier": "high", "use_cases": ["gaming", "content_creation"], "efficiency_rating": 8.2, "features": {"dram_cache": true, "heatsink": false}}'),

('WD Black 4TB', 'Western Digital', 'HDD', 'SATA', 120.00, 20, NULL, NULL, 8, NULL, NULL, NULL, NULL, NULL,
'{"interface": "SATA III", "rpm": "7200", "cache": "256MB", "form_factor": "3.5 inch"}',
'{"template_tier": "high", "use_cases": ["storage", "content_creation", "workstation"], "efficiency_rating": 7.5, "features": {"quiet": true, "enterprise": false}}'),

-- === BUDGET AM5 MOTHERBOARDS ($100-150) ===
('ASUS PRIME B650-PLUS', 'ASUS', 'Motherboard', 'AM5-DDR5', 125.00, 25, 'AM5', 'DDR5', 45, 'ATX', 305, 244, NULL, 1,
'{"chipset": "B650", "max_ram": "192GB", "pcie_slots": 2, "m2_slots": 2, "sata_ports": 4, "usb_ports": 15, "lan_speed": "2.5Gb", "pcie5_m2": 1}',
'{"template_tier": "entry", "use_cases": ["gaming", "productivity", "silent"], "features": {"wifi": false, "bluetooth": false, "rgb_headers": 3, "overclocking": true, "quiet_operation": true}, "expansion": {"max_memory_gb": 192, "memory_slots": 4, "pcie_x16_slots": 1, "m2_slots": 2}, "template_compatibility": {"silent_build": true}}'),

('MSI PRO B650-P WIFI', 'MSI', 'Motherboard', 'AM5-DDR5', 140.00, 20, 'AM5', 'DDR5', 50, 'ATX', 305, 244, NULL, 1,
'{"chipset": "B650", "max_ram": "128GB", "pcie_slots": 2, "m2_slots": 2, "sata_ports": 4, "usb_ports": 12, "lan_speed": "2.5Gb", "vrm_phases": "12+2+1"}',
'{"template_tier": "entry", "use_cases": ["gaming", "productivity", "silent"], "features": {"wifi": true, "bluetooth": true, "rgb_headers": 2, "overclocking": true, "quiet_operation": true}, "expansion": {"max_memory_gb": 128, "memory_slots": 4, "pcie_x16_slots": 1, "m2_slots": 2}, "template_compatibility": {"silent_build": true}}'),

('ASRock B650 Pro RS', 'ASRock', 'Motherboard', 'AM5-DDR5', 120.00, 30, 'AM5', 'DDR5', 42, 'ATX', 305, 244, NULL, 1,
'{"chipset": "B650", "max_ram": "128GB", "pcie_slots": 2, "m2_slots": 2, "sata_ports": 4, "usb_ports": 10, "lan_speed": "1Gb", "vrm_phases": "8+2+1"}',
'{"template_tier": "entry", "use_cases": ["gaming", "budget_gaming", "silent"], "features": {"wifi": false, "bluetooth": false, "rgb_headers": 1, "overclocking": true, "quiet_operation": true}, "expansion": {"max_memory_gb": 128, "memory_slots": 4, "pcie_x16_slots": 1, "m2_slots": 2}, "template_compatibility": {"silent_build": true}}'),

('Gigabyte B650 AORUS ELITE AX', 'Gigabyte', 'Motherboard', 'AM5-DDR5', 150.00, 18, 'AM5', 'DDR5', 48, 'ATX', 305, 244, NULL, 1,
'{"chipset": "B650", "max_ram": "128GB", "pcie_slots": 2, "m2_slots": 3, "sata_ports": 4, "usb_ports": 14, "lan_speed": "2.5Gb", "vrm_phases": "12+2+2"}',
'{"template_tier": "mid", "use_cases": ["gaming", "productivity", "streaming"], "features": {"wifi": true, "bluetooth": true, "rgb_headers": 2, "overclocking": true}, "expansion": {"max_memory_gb": 128, "memory_slots": 4, "pcie_x16_slots": 2, "m2_slots": 3}}'),

-- === MICRO-ATX AM5 MOTHERBOARDS ($80-130) ===
('ASRock B650M Pro4', 'ASRock', 'Motherboard', 'AM5-DDR5', 110.00, 22, 'AM5', 'DDR5', 40, 'Micro-ATX', 244, 244, NULL, 1,
'{"chipset": "B650", "max_ram": "128GB", "pcie_slots": 2, "m2_slots": 2, "sata_ports": 4, "usb_ports": 8, "lan_speed": "1Gb", "vrm_phases": "8+2+1"}',
'{"template_tier": "entry", "use_cases": ["gaming", "compact", "budget_gaming"], "features": {"wifi": false, "bluetooth": false, "rgb_headers": 1, "overclocking": true}, "expansion": {"max_memory_gb": 128, "memory_slots": 4, "pcie_x16_slots": 1, "m2_slots": 2}}'),

('MSI B650M PRO-B', 'MSI', 'Motherboard', 'AM5-DDR5', 95.00, 28, 'AM5', 'DDR5', 38, 'Micro-ATX', 244, 244, NULL, 1,
'{"chipset": "B650", "max_ram": "128GB", "pcie_slots": 2, "m2_slots": 2, "sata_ports": 4, "usb_ports": 8, "lan_speed": "1Gb", "vrm_phases": "6+2+1"}',
'{"template_tier": "entry", "use_cases": ["budget_gaming", "general", "compact"], "features": {"wifi": false, "bluetooth": false, "rgb_headers": 1, "overclocking": false}, "expansion": {"max_memory_gb": 128, "memory_slots": 4, "pcie_x16_slots": 1, "m2_slots": 2}}'),

('ASUS PRIME B650M-A', 'ASUS', 'Motherboard', 'AM5-DDR5', 125.00, 20, 'AM5', 'DDR5', 42, 'Micro-ATX', 244, 244, NULL, 1,
'{"chipset": "B650", "max_ram": "128GB", "pcie_slots": 2, "m2_slots": 2, "sata_ports": 4, "usb_ports": 10, "lan_speed": "1Gb", "vrm_phases": "8+2+1"}',
'{"template_tier": "entry", "use_cases": ["gaming", "productivity", "compact"], "features": {"wifi": false, "bluetooth": false, "rgb_headers": 2, "overclocking": true}, "expansion": {"max_memory_gb": 128, "memory_slots": 4, "pcie_x16_slots": 1, "m2_slots": 2}}'),

-- === EXISTING BUDGET MOTHERBOARDS (AM4) ===
('ASRock A520M-HDV', 'ASRock', 'Motherboard', 'AM4-DDR4', 60.00, 25, 'AM4', 'DDR4', 35, 'Micro-ATX', 244, 244, NULL, 1,
'{"chipset": "A520", "max_ram": "128GB", "pcie_slots": 1, "m2_slots": 1, "sata_ports": 4, "usb_ports": 6, "lan_speed": "1Gb", "vrm_phases": "3+2"}',
'{"template_tier": "entry", "use_cases": ["general", "budget_gaming"], "features": {"wifi": false, "bluetooth": false, "rgb_headers": 0, "overclocking": false}, "expansion": {"max_memory_gb": 128, "memory_slots": 2, "pcie_x16_slots": 1, "m2_slots": 1}}'),

('MSI B450M PRO-VDH MAX', 'MSI', 'Motherboard', 'AM4-DDR4', 75.00, 20, 'AM4', 'DDR4', 40, 'Micro-ATX', 244, 244, NULL, 1,
'{"chipset": "B450", "max_ram": "128GB", "pcie_slots": 2, "m2_slots": 1, "sata_ports": 6, "usb_ports": 8, "lan_speed": "1Gb", "vrm_phases": "4+2"}',
'{"template_tier": "entry", "use_cases": ["gaming", "productivity"], "features": {"wifi": false, "bluetooth": false, "rgb_headers": 1, "overclocking": true}, "expansion": {"max_memory_gb": 128, "memory_slots": 4, "pcie_x16_slots": 1, "m2_slots": 1}}'),

-- === NEW BUDGET LGA1700 MOTHERBOARDS ===
('MSI PRO B760M-P', 'MSI', 'Motherboard', 'LGA1700-DDR4', 90.00, 25, 'LGA1700', 'DDR4', 45, 'Micro-ATX', 244, 244, NULL, 1,
'{"chipset": "B760", "max_ram": "128GB", "pcie_slots": 2, "m2_slots": 2, "sata_ports": 4, "usb_ports": 8, "lan_speed": "1Gb", "vrm_phases": "8+1+1"}',
'{"template_tier": "entry", "use_cases": ["budget_gaming", "general"], "features": {"wifi": false, "bluetooth": false, "rgb_headers": 1, "overclocking": false}, "expansion": {"max_memory_gb": 128, "memory_slots": 4, "pcie_x16_slots": 1, "m2_slots": 2}}'),

('ASRock H610M-HVS', 'ASRock', 'Motherboard', 'LGA1700-DDR4', 65.00, 30, 'LGA1700', 'DDR4', 35, 'Micro-ATX', 244, 244, NULL, 1,
'{"chipset": "H610", "max_ram": "64GB", "pcie_slots": 1, "m2_slots": 1, "sata_ports": 4, "usb_ports": 6, "lan_speed": "1Gb", "vrm_phases": "4+1+1"}',
'{"template_tier": "entry", "use_cases": ["budget", "general"], "features": {"wifi": false, "bluetooth": false, "rgb_headers": 0, "overclocking": false}, "expansion": {"max_memory_gb": 64, "memory_slots": 2, "pcie_x16_slots": 1, "m2_slots": 1}}'),

('ASUS PRIME B660M-A', 'ASUS', 'Motherboard', 'LGA1700-DDR4', 115.00, 20, 'LGA1700', 'DDR4', 48, 'Micro-ATX', 244, 244, NULL, 1,
'{"chipset": "B660", "max_ram": "128GB", "pcie_slots": 2, "m2_slots": 2, "sata_ports": 4, "usb_ports": 10, "lan_speed": "1Gb", "vrm_phases": "8+1+1"}',
'{"template_tier": "entry", "use_cases": ["gaming", "productivity"], "features": {"wifi": false, "bluetooth": false, "rgb_headers": 1, "overclocking": false}, "expansion": {"max_memory_gb": 128, "memory_slots": 4, "pcie_x16_slots": 1, "m2_slots": 2}}'),

-- === LGA1200 COMPATIBILITY (Older CPU Support) ===
('MSI B460M PRO', 'MSI', 'Motherboard', 'LGA1200-DDR4', 85.00, 15, 'LGA1200', 'DDR4', 40, 'Micro-ATX', 244, 244, NULL, 1,
'{"chipset": "B460", "max_ram": "128GB", "pcie_slots": 2, "m2_slots": 1, "sata_ports": 6, "usb_ports": 8, "lan_speed": "1Gb", "vrm_phases": "4+1+1"}',
'{"template_tier": "entry", "use_cases": ["general", "legacy_build"], "features": {"wifi": false, "bluetooth": false, "rgb_headers": 0, "overclocking": false}, "expansion": {"max_memory_gb": 128, "memory_slots": 4, "pcie_x16_slots": 1, "m2_slots": 1}}'),

('ASRock B760M PRO', 'ASRock', 'Motherboard', 'LGA1700-DDR5', 120.00, 18, 'LGA1700', 'DDR5', 50, 'Micro-ATX', 244, 244, NULL, 1, 
'{"chipset": "B760", "max_ram": "128GB", "pcie_slots": 2, "m2_slots": 2, "sata_ports": 4, "usb_ports": 8, "lan_speed": "1Gb", "vrm_phases": "8+1+1"}',
'{"template_tier": "entry", "use_cases": ["gaming", "productivity"], "features": {"wifi": false, "bluetooth": false, "rgb_headers": 1, "overclocking": false}, "expansion": {"max_memory_gb": 128, "memory_slots": 4, "pcie_x16_slots": 1, "m2_slots": 2}}'),

-- === MID-RANGE MOTHERBOARDS ===
('MSI PRO Z790-A DDR5', 'MSI', 'Motherboard', 'LGA1700-DDR5', 210.00, 12, 'LGA1700', 'DDR5', 70, 'ATX', 305, 244, NULL, 1, 
'{"chipset": "Z790", "max_ram": "128GB", "pcie_slots": 3, "m2_slots": 4, "sata_ports": 6, "usb_ports": 14, "lan_speed": "2.5Gb", "vrm_phases": "14+1+1"}',
'{"template_tier": "mid", "use_cases": ["gaming", "productivity", "streaming"], "features": {"wifi": false, "bluetooth": false, "rgb_headers": 2, "overclocking": true}, "expansion": {"max_memory_gb": 128, "memory_slots": 4, "pcie_x16_slots": 2, "m2_slots": 4}}'),

('ASUS ROG STRIX B650E-F', 'ASUS', 'Motherboard', 'AM5-DDR5', 250.00, 10, 'AM5', 'DDR5', 75, 'ATX', 305, 244, NULL, 1, 
'{"chipset": "B650E", "max_ram": "128GB", "pcie_slots": 3, "m2_slots": 3, "sata_ports": 4, "usb_ports": 16, "lan_speed": "2.5Gb", "vrm_phases": "16+2+1"}',
'{"template_tier": "mid", "use_cases": ["gaming", "productivity", "content_creation"], "features": {"wifi": true, "bluetooth": true, "rgb_headers": 4, "overclocking": true}, "expansion": {"max_memory_gb": 128, "memory_slots": 4, "pcie_x16_slots": 2, "m2_slots": 3}}'),

-- === HIGH-END MOTHERBOARDS ===
('ASUS TUF Gaming X670E-Plus', 'ASUS', 'Motherboard', 'AM5-DDR5', 290.00, 8, 'AM5', 'DDR5', 80, 'ATX', 305, 244, NULL, 1, 
'{"chipset": "X670E", "max_ram": "128GB", "pcie_slots": 4, "m2_slots": 4, "sata_ports": 8, "usb_ports": 18, "lan_speed": "2.5Gb", "vrm_phases": "16+2+1"}',
'{"template_tier": "high", "use_cases": ["content_creation", "workstation", "gaming"], "features": {"wifi": true, "bluetooth": true, "rgb_headers": 3, "overclocking": true}, "expansion": {"max_memory_gb": 128, "memory_slots": 4, "pcie_x16_slots": 3, "m2_slots": 4}}'),

-- === BUDGET / ENTRY-LEVEL PSUs ===
('EVGA 600 BR 600W', 'EVGA', 'PSU', 'ATX', 58.00, 30, NULL, NULL, 600, NULL, NULL, NULL, 'ATX', NULL,
 '{"efficiency": "80+ Bronze", "modular": "Non-Modular", "warranty_years": 3}',
 '{"template_tier": "entry", "use_cases": ["budget_gaming", "general"], "efficiency_rating": 6.0, "features": {"modular": false, "quiet": false, "rgb": false}}'),

('Corsair CV650', 'Corsair', 'PSU', 'ATX', 65.00, 35, NULL, NULL, 650, NULL, NULL, NULL, 'ATX', NULL,
 '{"efficiency": "80+ Bronze", "modular": "Non-Modular", "warranty_years": 3}',
 '{"template_tier": "entry", "use_cases": ["budget_gaming", "general", "silent"], "efficiency_rating": 6.5, "features": {"modular": false, "quiet": true, "rgb": false}, "template_compatibility": {"budget_gaming": true, "silent_build": true}}'),

('EVGA BR 700W', 'EVGA', 'PSU', 'ATX', 72.00, 30, NULL, NULL, 700, NULL, NULL, NULL, 'ATX', NULL,
 '{"efficiency": "80+ Bronze", "modular": "Non-Modular", "warranty_years": 3}',
 '{"template_tier": "entry", "use_cases": ["gaming", "budget_gaming"], "efficiency_rating": 6.8, "features": {"modular": false, "quiet": false, "rgb": false}, "template_compatibility": {"budget_gaming": true}}'),

('Corsair CX650M', 'Corsair', 'PSU', 'ATX', 78.00, 25, NULL, NULL, 650, NULL, NULL, NULL, 'ATX', NULL,
 '{"efficiency": "80+ Bronze", "modular": "Semi-Modular", "warranty_years": 3}',
 '{"template_tier": "entry", "use_cases": ["gaming", "productivity", "silent"], "efficiency_rating": 7.0, "features": {"modular": true, "quiet": true, "rgb": false}, "template_compatibility": {"silent_build": true}}'),

-- === MID-RANGE PSUs ===
('Seasonic Focus GX-750', 'Seasonic', 'PSU', 'ATX', 112.00, 20, NULL, NULL, 750, NULL, NULL, NULL, 'ATX', NULL,
 '{"efficiency": "80+ Gold", "modular": "Fully Modular", "warranty_years": 10}',
 '{"template_tier": "mid", "use_cases": ["gaming", "content_creation", "quiet"], "efficiency_rating": 8.5, "features": {"modular": true, "quiet": true, "rgb": false}}'),

('Corsair RM750x', 'Corsair', 'PSU', 'ATX', 118.00, 18, NULL, NULL, 750, NULL, NULL, NULL, 'ATX', NULL,
 '{"efficiency": "80+ Gold", "modular": "Fully Modular", "warranty_years": 10}',
 '{"template_tier": "mid", "use_cases": ["gaming", "streaming", "content_creation"], "efficiency_rating": 8.2, "features": {"modular": true, "quiet": true, "rgb": false}}'),

('MSI MPG A750GF', 'MSI', 'PSU', 'ATX', 108.00, 22, NULL, NULL, 750, NULL, NULL, NULL, 'ATX', NULL,
 '{"efficiency": "80+ Gold", "modular": "Fully Modular", "warranty_years": 10}',
 '{"template_tier": "mid", "use_cases": ["gaming", "productivity"], "efficiency_rating": 8.0, "features": {"modular": true, "quiet": false, "rgb": true}}'),

-- === HIGH-END PSUs ===
('Corsair RM850x 850W', 'Corsair', 'PSU', 'ATX', 142.00, 15, NULL, NULL, 850, NULL, NULL, NULL, 'ATX', NULL,
 '{"efficiency": "80+ Gold", "modular": "Fully Modular", "warranty_years": 10}',
 '{"template_tier": "high", "use_cases": ["gaming", "content_creation", "multi_gpu"], "efficiency_rating": 9.0, "features": {"modular": true, "quiet": true, "rgb": false}}'),

('Corsair SF750 750W SFX', 'Corsair', 'PSU', 'SFX', 175.00, 10, NULL, NULL, 750, NULL, NULL, NULL, 'SFX', NULL,
 '{"efficiency": "80+ Platinum", "modular": "Fully Modular", "warranty_years": 7}',
 '{"template_tier": "high", "use_cases": ["compact", "mini_itx", "gaming"], "efficiency_rating": 8.5, "features": {"modular": true, "quiet": true, "rgb": false}}'),

-- === BUDGET CASES ===
('Cooler Master MasterBox Q300L', 'Cooler Master', 'Case', 'Micro-ATX', 45.00, 20, NULL, NULL, NULL, 'Micro-ATX, Mini-ITX', 360, 159, 'ATX', NULL,
'{"usb_ports": 2, "front_io": "2x USB 3.0 Type-A, Audio In/Out", "radiator_support": "240mm front, 120mm rear"}',
'{"template_tier": "entry", "use_cases": ["compact", "budget_gaming"], "features": {"tempered_glass": false, "rgb": false, "sound_dampening": false, "good_airflow": true}}'),

('Silverstone FARA R1', 'Silverstone', 'Case', 'ATX', 58.00, 30, NULL, NULL, NULL, 'ATX', 350, 155, 'ATX', NULL,
'{"usb_ports": 2, "front_io": "USB 3.0 Type-A", "radiator_support": "120mm rear"}',
'{"template_tier": "entry", "use_cases": ["budget_gaming", "general"], "features": {"tempered_glass": true, "rgb": false, "sound_dampening": false, "good_airflow": true}}'),

('Phanteks Eclipse P300A', 'Phanteks', 'Case', 'ATX', 62.00, 18, NULL, NULL, NULL, 'ATX', 390, 160, 'ATX', NULL,
'{"usb_ports": 2, "front_io": "USB 3.0 Type-A", "radiator_support": "280mm front"}',
'{"template_tier": "entry", "use_cases": ["gaming", "budget_gaming"], "features": {"tempered_glass": true, "rgb": false, "sound_dampening": false, "excellent_airflow": true}}'),

-- === MID-RANGE CASES ===
('Cooler Master MasterBox MB511', 'Cooler Master', 'Case', 'ATX', 68.00, 25, NULL, NULL, NULL, 'ATX', 418, 165, 'ATX', NULL,
'{"usb_ports": 2, "front_io": "USB 3.0 Type-A", "radiator_support": "240mm front"}',
'{"template_tier": "mid", "use_cases": ["gaming", "budget_gaming"], "features": {"tempered_glass": true, "rgb": true, "sound_dampening": false, "excellent_airflow": true}}'),

('NZXT H6 Flow', 'NZXT', 'Case', 'ATX', 85.00, 15, NULL, NULL, NULL, 'ATX', 381, 428, 'ATX', NULL, 
'{"usb_ports": 3, "front_io": "USB-C", "radiator_support": "280mm front"}',
'{"template_tier": "mid", "use_cases": ["gaming", "productivity"], "features": {"tempered_glass": true, "rgb": false, "sound_dampening": false, "good_airflow": true}}'),

('Lian Li Lancool 216', 'Lian Li', 'Case', 'ATX', 105.00, 12, NULL, NULL, NULL, 'ATX', 395, 418, 'ATX', NULL, 
'{"usb_ports": 3, "front_io": "USB-C", "radiator_support": "360mm front"}',
'{"template_tier": "mid", "use_cases": ["gaming", "streaming", "content_creation"], "features": {"tempered_glass": true, "rgb": true, "sound_dampening": false, "excellent_airflow": true}}'),

('Fractal Design Define 7 Compact', 'Fractal Design', 'Case', 'ATX', 115.00, 10, NULL, NULL, NULL, 'ATX', 315, 402, 'ATX', NULL,
'{"usb_ports": 2, "front_io": "USB-C", "radiator_support": "280mm front"}',
'{"template_tier": "mid", "use_cases": ["quiet", "productivity", "home_office"], "features": {"tempered_glass": false, "rgb": false, "sound_dampening": true, "good_airflow": true}}'),

-- === HIGH-END CASES ===
('NZXT H7 Flow', 'NZXT', 'Case', 'ATX', 130.00, 8, NULL, NULL, NULL, 'ATX', 400, 465, 'ATX', NULL,
'{"usb_ports": 3, "front_io": "USB-C", "radiator_support": "420mm front"}',
'{"template_tier": "high", "use_cases": ["gaming", "content_creation", "enthusiast"], "features": {"tempered_glass": true, "rgb": false, "sound_dampening": false, "excellent_airflow": true}}'),

('NZXT H7 Elite', 'NZXT', 'Case', 'ATX', 200.00, 8, NULL, NULL, NULL, 'ATX', 400, 465, 'ATX', NULL,
'{"usb_ports": 3, "front_io": "USB-C", "radiator_support": "360mm front"}',
'{"template_tier": "high", "use_cases": ["gaming", "content_creation", "enthusiast"], "features": {"tempered_glass": true, "rgb": true, "sound_dampening": false, "excellent_airflow": true}}'),

-- === BUDGET COOLERS ===
('Cooler Master Hyper 212 EVO V2', 'Cooler Master', 'Cooler', 'Universal', 35.00, 40, 'LGA1700,AM5,AM4', NULL, NULL, 'Tower', NULL, 159, NULL, NULL, 
'{"fan_count": 1, "noise_level": "27 dBA"}',
'{"template_tier": "entry", "use_cases": ["general", "budget_gaming"], "features": {"rgb": false, "quiet_operation": false, "low_noise": false}}'),

-- === MID-RANGE COOLERS ===
('be quiet! Dark Rock 4', 'be quiet!', 'Cooler', 'Universal', 78.00, 18, 'LGA1700,AM5,AM4', NULL, NULL, 'Tower', NULL, 159, NULL, NULL, 
'{"fan_count": 1, "noise_level": "21.4 dBA"}',
'{"template_tier": "mid", "use_cases": ["quiet", "productivity", "home_office", "silent"], "features": {"rgb": false, "quiet_operation": true, "low_noise": true}}'),

('Corsair H100i RGB Elite', 'Corsair', 'Cooler', 'Universal', 115.00, 12, 'LGA1700,AM5,AM4', NULL, NULL, 'Liquid', NULL, 27, NULL, NULL, 
'{"fan_count": 2, "radiator_size": "240mm", "pump_speed": "2400 RPM", "noise_level": "25 dBA"}',
'{"template_tier": "mid", "use_cases": ["gaming", "overclocking", "content_creation"], "features": {"rgb": true, "quiet_operation": false, "low_noise": false}}'),

-- === HIGH-END COOLERS ===
('Noctua NH-D15', 'Noctua', 'Cooler', 'Universal', 105.00, 15, 'LGA1700,AM5,AM4', NULL, NULL, 'Tower', NULL, 165, NULL, NULL, 
'{"fan_count": 2, "noise_level": "24.6 dBA"}',
'{"template_tier": "high", "use_cases": ["quiet", "overclocking", "workstation"], "features": {"rgb": false, "quiet_operation": true, "low_noise": true}}'),

('Arctic Liquid Freezer II 280', 'Arctic', 'Cooler', 'Universal', 95.00, 10, 'LGA1700,AM5,AM4', NULL, NULL, 'Liquid', NULL, 38, NULL, NULL, 
'{"fan_count": 3, "radiator_size": "280mm", "pump_speed": "2000 RPM", "noise_level": "23 dBA"}',
'{"template_tier": "high", "use_cases": ["gaming", "content_creation", "overclocking"], "features": {"rgb": false, "quiet_operation": true, "low_noise": true}}'),

-- === ENTHUSIAST COOLERS ===
('Corsair H150i Elite LCD XT', 'Corsair', 'Cooler', 'Universal', 260.00, 6, 'LGA1700,AM5,AM4', NULL, NULL, 'Liquid', NULL, 27, NULL, NULL,
'{"fan_count": 3, "radiator_size": "360mm", "pump_speed": "2400 RPM", "noise_level": "27 dBA"}',
'{"template_tier": "enthusiast", "use_cases": ["gaming", "content_creation", "overclocking", "enthusiast"], "efficiency_rating": 8.0, "features": {"rgb": true, "quiet_operation": false, "low_noise": false}}');