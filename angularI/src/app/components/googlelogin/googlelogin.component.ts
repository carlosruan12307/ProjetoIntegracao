import { Component, NgZone } from '@angular/core';
import { Router } from '@angular/router';
import { CredentialResponse } from 'google-one-tap';
import { HttpServiceService } from 'src/app/services/http-service.service';

@Component({
  selector: 'app-googlelogin',
  templateUrl: './googlelogin.component.html',
  styleUrls: ['./googlelogin.component.css'],
})
export class GoogleloginComponent {
  constructor(
    private route: Router,
    private service: HttpServiceService,
    private ngzone: NgZone
  ) {}

  mouse: string = 'notenter';
  teste: number = 0;
  tururu: any;
  left: number = 0;
  ngOnInit(): void {
    // @ts-ignore
    window.onGoogleLibraryLoad = () => {
      //@ts-ignore
      google.accounts.id.initialize({
        client_id:
          '336886880384-33mjig685qfsgvaivcbqkro60n6749k6.apps.googleusercontent.com',
        callback: this.handleResponse.bind(this),
        auto_select: false,
        cancel_on_tap_outside: true,
      });
      //@ts-ignore
      google.accounts.id.renderButton(document.getElementById('buttonT'), {
        theme: 'outline',
        size: 'large',
        width: '100%',
      });
      //@ts-ignore
      google.accounts.id.prompt((notification: PromptMomentNotification) => {});
    };
  }

  async handleResponse(response: CredentialResponse) {
    await this.service.LoginWithGoogle(response.credential).subscribe({
      next: (x) => {
        localStorage.setItem('token', x.token);
        this.service.LoggedIn = true;
        this.ngzone.run(() => {
          this.route.navigate(['/home']);
        });
      },
      error: (error) => {
        console.log(response);

        this.ngzone.run(() => {
          this.route.navigate(['']);
        });
        console.log(error);
      },
    });
  }
  handleClick() {}
}
