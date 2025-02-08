class CreditCardDocument:
    def __init__(self,
                 id: str = "",
                 name: str = "",
                 parent_company: str = "",
                 suitable_for: list[str] = None,  # e.g., travel, fuel, lifestyle
                 lounge_benefits: bool = False,
                 extra_benefits: list[str] = None,  # Extra feature engineering possibilities
                 annual_fee: float = 0.0,
                 premium: bool = False,
                 interest_rate: float = 0.0,
                 foreign_transaction_fee: float = 0.0,
                 fuel_surcharge_waiver: bool = False,
                 welcome_bonus: str = "",
                 eligibility_criteria: str = "",
                 issued_by: str = "",
                 max_cashback_limit: float = 0.0):
        """
        Initialize a `CreditCardDocument` object with default or provided values.

        :param id: Unique identifier for the credit card.
        :param name: Name of the credit card.
        :param parent_company: Parent company running this card.
        :param suitable_for: Categories this card is suitable for (e.g., travel, fuel, lifestyle).
        :param lounge_benefits: Whether the card provides lounge benefits.
        :param extra_benefits: List of additional benefits/features engineered.
        :param annual_fee: Annual fee for the cardholder.
        :param premium: Indicates whether the card is a premium offering or not.
        :param interest_rate: Interest rate applicable.
        :param foreign_transaction_fee: Fee for foreign transactions as a percentage.
        :param fuel_surcharge_waiver: Indicates if the card offers a fuel surcharge waiver.
        :param welcome_bonus: Description of the welcome bonus (if any).
        :param eligibility_criteria: Eligibility requirements to apply for the card.
        :param issued_by: Issuer/bank of the credit card.
        :param max_cashback_limit: Maximum cashback limit offered.
        """
        self.id: str = id
        self.name: str = name
        self.parent_company: str = parent_company
        self.suitable_for: list[str] = suitable_for if suitable_for else []
        self.lounge_benefits: bool = lounge_benefits
        self.extra_benefits: list[str] = extra_benefits if extra_benefits else []
        self.annual_fee: float = annual_fee
        self.premium: bool = premium
        self.interest_rate: float = interest_rate
        self.foreign_transaction_fee: float = foreign_transaction_fee
        self.fuel_surcharge_waiver: bool = fuel_surcharge_waiver
        self.welcome_bonus: str = welcome_bonus
        self.eligibility_criteria: str = eligibility_criteria
        self.issued_by: str = issued_by
        self.max_cashback_limit: float = max_cashback_limit

    def __str__(self) -> str:
        """
        Returns a human-readable string representation of the object.
        """
        return f"CreditCardDocument(id={self.id}, name={self.name}, parent_company={self.parent_company}, " \
               f"suitable_for={self.suitable_for}, lounge_benefits={self.lounge_benefits}, " \
               f"extra_benefits={self.extra_benefits}, annual_fee={self.annual_fee}, premium={self.premium}, " \
               f"interest_rate={self.interest_rate}, foreign_transaction_fee={self.foreign_transaction_fee}, " \
               f"fuel_surcharge_waiver={self.fuel_surcharge_waiver}, welcome_bonus={self.welcome_bonus}, " \
               f"eligibility_criteria={self.eligibility_criteria}, issued_by={self.issued_by}, " \
               f"max_cashback_limit={self.max_cashback_limit})"

    def display_card_info(self) -> None:
        """
        Prints out formatted credit card information for user review.
        """
        print(f"Credit Card Details:")
        print(f"  ID: {self.id}")
        print(f"  Name: {self.name}")
        print(f"  Parent Company: {self.parent_company}")
        print(f"  Issuer: {self.issued_by}")
        print(f"  Suitable For: {', '.join(self.suitable_for) if self.suitable_for else 'None'}")
        print(f"  Lounge Benefits: {'Yes' if self.lounge_benefits else 'No'}")
        print(f"  Extra Benefits: {', '.join(self.extra_benefits) if self.extra_benefits else 'None'}")
        print(f"  Annual Fee: ₹{self.annual_fee}")
        print(f"  Premium: {'Yes' if self.premium else 'No'}")
        print(f"  Interest Rate: {self.interest_rate}%")
        print(f"  Foreign Transaction Fee: {self.foreign_transaction_fee}%")
        print(f"  Fuel Surcharge Waiver: {'Yes' if self.fuel_surcharge_waiver else 'No'}")
        print(f"  Welcome Bonus: {self.welcome_bonus if self.welcome_bonus else 'None'}")
        print(f"  Eligibility Criteria: {self.eligibility_criteria if self.eligibility_criteria else 'None'}")
        print(f"  Max Cashback Limit: ₹{self.max_cashback_limit}")
