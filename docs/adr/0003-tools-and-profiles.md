# ADR 0003 — Native assembly and board profiles

**Accepted direction.** Build a Java assembler for the documented W65C02 instruction set. Share opcode metadata with disassembly, use independent reference encodings for verification, and avoid external assembler binaries in the runtime. Assemble to validated ROM images with explicit vectors and profile-specific I/O symbols. Use versioned profile data with exact overlap validation for both canonical and aliased ranges. The extended profile targets the reference schematic, and the expanded profile is a documented synthetic board.
