export class BusinessItem {
  public id: number;
  public index: number;
  public businessName: string;
  public description: string;
  public createDate: Date;
  public lastUpdateDate: Date;
  public expiredDate: Date;
  public founder: number;

  constructor(businessName: string, desc: string, founder: number, createDate: Date, lastUpdateDate: Date, expiredDate: Date) {
    this.businessName = businessName;
    this.description = desc;
    this.founder = founder;
    this.createDate = createDate;
    this.lastUpdateDate = lastUpdateDate;
    this.expiredDate = expiredDate;
  }
}
