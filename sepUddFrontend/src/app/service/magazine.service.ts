import {Injectable, Injector} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {UserService} from './user.service';
import {TokenInterceptorService} from "./security/token-interceptor";

@Injectable({
  providedIn: 'root'
})
export class MagazineService {

  constructor(private http: HttpClient, private userService: UserService, private inj: Injector) {
  }

  getAllMagazines(): any {
    return this.http.get('api/magazines/getAll');
  }

  buyMagazine(id): any {
    const tokenint = new TokenInterceptorService(this.inj);
    return this.http.get('api/magazines/buy/'.concat(id));
  }

  complete(url): any {
    return this.http.get(url);
  }

  errorComplete(error): any {
    return this.http.get(error);
  }

}
