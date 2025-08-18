# Tutorial

This tutorial will guide you through setting up your desktop application with everything ready to go.

## 💖 Support the project and get featured as a contributor!
If this project has been helpful or inspiring, consider making a donation to help it grow.

As a thank you, your name will be proudly listed in the README.
[Donate now](buymeacoffee.com/plantfall)

## Getting the Application

Here you going to see the ways you can use to initialize your own project.

See Video Tutorial
[Watch Now](https://youtu.be/HJPHG8Bqq98)

There are two ways to initialize your own project:

1. Download the app archive  
   [Click here to download `coesion_effect_app_v5.zip`](https://github.com/eliezerBrasilian/Coesion-Effect/releases/download/v5/my_coesion_effect_app_v5.zip)

2. Or, on Windows, open PowerShell and run:

   ```powershell
   Invoke-WebRequest -Uri "https://github.com/eliezerBrasilian/Coesion-Effect/releases/download/v5/my_coesion_effect_app_v5.zip" -OutFile "coesion_effect_app_v5.zip"
   ```

Then extract the archive and open the folder in your preferred IDE like VS Code. You're now ready to dive in!


## Building your App

```bash
mvn clean package
```

## Distributing Your App

After you have builded it you can distribute easily

Enter manually into scripts folder and run create-installer.bat

```bash
cd scripts
```

```bash
.\create-installer.bat
```

After that, your app will be generated in the `dist` folder:

- The `.exe` will be inside `dist/MyApp`
- The `.msi` installer will be inside `dist/`

## Testing Your App Programatically

In order to test your app, you need.

Enter manually into scripts folder and run test-app.bat

```bash
cd scripts
```

```bash
.\test-app.bat
```

## Customizing Your App

To update metadata like description, icon, version, and vendor name, edit the `jpackage` section inside `scripts/create-installer.bat.`

## Contribute

Want to contribute?
Feel free to open a PR and become part of the team behind this open-source project!

## Requirements

Make sure you have the following installed for building purposes:

- WiX Toolset (required to generate MSI installers)
  [Download WixToolset 3.14.1(wix314.exe)](https://github.com/wixtoolset/wix3/releases/tag/wix3141rtm)

Then install the app and procced with installation steps. After that you have to set the variable path.
![wix_tollset_path](https://github.com/user-attachments/assets/d92cc6ec-fdd9-4eac-bb82-1c878fa66937)
