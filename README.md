## Distributing your desktop application

To distribute your aplication is so easy. You must have to build your app, finally create an .exe file. Lets dive into it.

> you can also test it programatically using command line.

## Building you app

```bash
cryxie build --mvn
```

Or you can use Maven directly

```bash
mvn clean package
```

## Distributing your app

After you have builded it you can distribute easily on two ways

1. Run the folloywing command with cryxie-cli

```bash
cryxie dist-javafx-app --windows
```

2. Or you can enter manually into scripts folder and run create-installer.bat

```bash
cd scripts
```

```bash
.\create-installer.bat
```

## After that, your app will be generated into dist folder

- Your .exe app will be inside dist/MyApp
- Your msi will be inside dist/

## Testing your app programatically

In order to test your app, you have two options.

1. Run the folloywing command with cryxie-cli

```bash
cryxie test-javafx-app --windows
```

2. Or you can enter manually into scripts folder and run test-app.bat

```bash
cd scripts
```

```bash
.\test-app.bat
```

## Personalizing your app

To personalize your app description, icon, version, vendor and etc... You must edit jpackage inside scripts/create-installer.bat

## Contribute now

If you desire, you can contribute with this open source project.
Feel free to contribute and be part of the team

## Warning

You must have installed on your machine:

- WiX Toolset (to generate your msi)
  download link:
  https://github.com/wixtoolset/wix3/releases/tag/wix3141rtm
  > download the option: wix314.exe

Then install the app and procced with installation steps. After that you have to set the variable path.
![wix_tollset_path](https://github.com/user-attachments/assets/d92cc6ec-fdd9-4eac-bb82-1c878fa66937)


- Cryxie-cli (if you want to run native commands easily)
  https://cryxie.com/documentation/installation-guide
