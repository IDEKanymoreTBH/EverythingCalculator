public enum MolarMass {
    H("hydrogen", 1.0078),
    He("helium", 4.0026),
    Li("lithium", 6.9410),
    Be("beryllium", 9.0122),
    B("boron", 10.811),
    C("carbon", 12.011),
    N("nitrogen", 14.007),
    O("oxygen", 15.999),
    F("fluorine", 18.998),
    Ne("neon", 20.180),
    Na("sodium", 22.990),
    Mg("magnesium", 24.305),
    Al("aluminium", 26.982),
    Si("silicon", 28.086),
    P("phosphorus", 30.974),
    S("sulfur", 32.065),
    Cl("chlorine", 35.453),
    Ar("argon", 39.948),
    K("potassium", 39.098),
    Ca("calcium", 40.078),
    Sc("scandium", 44.956),
    Ti("titanium", 47.867),
    V("vanadium", 50.942),
    Cr("chromium", 51.996),
    Mn("manganese", 54.938),
    Fe("iron", 55.845),
    Co("cobalt", 58.933),
    Ni("nickel", 58.693),
    Cu("copper", 63.546),
    Zn("zinc", 65.380),
    Ga("gallium", 69.723),
    Ge("germanium", 72.640),
    As("arsenic", 74.922),
    Se("selenium", 78.960),
    Br("bromine", 79.904),
    Kr("krypton", 83.798),
    Rb("rubidium", 85.468),
    Sr("strontium", 87.620),
    Y("yttrium", 88.906),
    Zr("zirconium", 91.224),
    Nb("niobium", 92.906),
    Mo("molybdenum", 95.950),
    Tc("technetium", 98),
    Ru("ruthenium", 101.07),
    Rh("rhodium", 102.91),
    Pd("palladium", 106.42),
    Ag("silver", 107.87),
    Cd("cadmium", 112.41),
    In("indium", 114.82),
    Sn("tin", 118.71),
    Sb("antimony", 121.76),
    Te("tellurium", 127.60),
    I("iodine", 126.90),
    Xe("xenon", 131.29),
    Cs("caesium", 132.91),
    Ba("barium", 137.33),
    La("lanthanum", 138.91),
    Ce("cerium", 140.12),
    Pr("praseodymium", 140.91),
    Nd("neodymium", 144.24),
    Pm("promethium", 145),
    Sm("samarium", 150.36),
    Eu("europium", 151.96),
    Gd("gadolinium", 157.25),
    Tb("terbium", 158.93),
    Dy("dysprosium", 162.5),
    Ho("holmium", 164.93),
    Er("erbium", 167.26),
    Tm("thulium", 168.93),
    Yb("ytterbium", 173.04),
    Lu("lutetium", 174.97),
    Hf("hafnium", 178.49),
    Ta("tantalum", 180.95),
    W("tungsten", 183.84),
    Re("rhenium", 186.21),
    Os("osmium", 190.23),
    Ir("iridium", 192.22),
    Pt("platinum", 195.08),
    Au("gold", 196.97),
    Hg("mercury", 200.59),
    Tl("thallium", 204.38),
    Pb("lead", 207.20),
    Bi("bismuth", 208.98),
    Po("polonium", 209),
    At("astatine", 210),
    Rn("radon", 222),
    Fr("francium", 223),
    Ra("radium", 226),
    Ac("actinium", 227),
    Th("thorium", 232.04),
    Pa("protactinium", 231.04),
    U("uranium", 238.03),
    Np("neptunium", 237.05),
    Pu("plutonium", 244),
    Am("americium", 243),
    Cm("curium", 247),
    Bk("berkelium", 247),
    Cf("californium", 251),
    Es("einsteinium", 252),
    Fm("fermium", 257),
    Md("mendelevium", 258),
    No("nobelium",259),
    Lr("lawrencium", 262),
    Rf("rutherfordium", 267),
    Db("dubnium", 262),
    Sg("seaborgium", 269),
    Bh("bohrium", 264),
    Hs("hassium", 269),
    Mt("meitnerium", 278),
    Ds("darmstadtium", 281),
    Rg("roentgenium", 282),
    Cn("copernicium", 285),
    Nh("nihonium", 286),
    Fl("flerovium", 289),
    Mc("moscovium", 289),
    Lv("livermorium", 293),
    Ts("tennessine", 294),
    Og("oganesson", 294);
    MolarMass(String elementName, double mass) {
        this.elementName = elementName;
        this.mass = mass;
    }
    private final String elementName;
    private final double mass;
    public static double getForMass(String elementName) throws UnknownElementException {
        for(int i = 0; i < values().length; i++) {
            if(values()[i].elementName.equals(elementName)) {
                return values()[i].mass;
            }
        }
        throw new UnknownElementException(String.format("Unknown Element Of Name '%s'", elementName));
    }
    public String getName(MolarMass mm) {
        return mm.elementName;
    }
    public double getMass(MolarMass mm) {
        return mm.mass;
    }
}
/**An Exception Thrown When The User Of {@code MolarMass.getForMass()} Enters An Element Name Not On The Periodic Table*/
class UnknownElementException extends EveryCalcException {
    public UnknownElementException() {
        super();
    }
    public UnknownElementException(String msg) {
        super(msg);
    }
    public UnknownElementException(Throwable t) {
        super(t);
    }
    public UnknownElementException(String msg, Throwable t) {
        super(msg, t);
    }
}