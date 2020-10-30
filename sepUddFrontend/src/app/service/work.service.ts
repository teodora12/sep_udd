import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from "rxjs";
import {TokenInterceptorService} from "./security/token-interceptor";

@Injectable({
  providedIn: 'root'
})
export class WorkService {

  constructor(private http: HttpClient) {
  }

  getWorksByMagazineId(id: any): any {
    return this.http.get('api/works/getWorks/' + id);
  }

  buyWork(id): any {
    return this.http.get('api/works/buy/'.concat(id));
  }

  complete(url): any {
    return this.http.get(url);
  }

  errorComplete(error): any {
    return this.http.get(error);
  }
  
  submitWorkData(workData): any {
    return this.http.post('api/works/submitWorkData', workData);
  }

  search(param, phrase, type): any {
    return this.http.get('api/works/get/' + param + '/' + phrase + '/' + type);
  }

  getScientificFields(): any {
    return this.http.get('api/works/getScientificFields');
  }

  advancedSearch(advancedSearch) {
    return this.http.post('api/works/get/advancedSearch', advancedSearch);
  }

  downloadWork(id: any) {

    const httpOptions = {
      'responseType': 'arraybuffer' as 'json'
    };
    return this.http.get('api/works/downloadWork/get/' + id, httpOptions) as Observable<any>;

  }
}
