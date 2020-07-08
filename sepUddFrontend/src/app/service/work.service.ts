import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class WorkService {

  constructor(private http: HttpClient) { }


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
      'responseType'  : 'arraybuffer' as 'json'
    };
    return this.http.get('api/works/downloadWork/get/' + id, httpOptions) as Observable<any>;
    
  }
}
