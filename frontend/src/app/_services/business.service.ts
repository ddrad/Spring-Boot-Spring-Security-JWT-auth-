import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {BusinessItem, User} from "../_models";
import {environment} from "../../environments/environment";
import {Subject} from "rxjs/index";

@Injectable()
export class BusinessService {

  businessChangedSbj = new Subject<BusinessItem[]>();

  constructor(private http: HttpClient) {
  }

  public updateBbusinessSbj(bus:BusinessItem[]) {
    this.businessChangedSbj.next(bus.slice());
  }
  getAll() {
    return this.http.get<BusinessItem[]>(`${environment.busApiUrl}/vehicles`);
  }

}
